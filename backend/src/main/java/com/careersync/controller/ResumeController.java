package com.careersync.controller;

import com.careersync.model.Resume;
import com.careersync.repository.ResumeRepository;
import com.careersync.security.UserPrincipal;
import com.careersync.service.AiService;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/api/v1/resume")
public class ResumeController {

    private final ResumeRepository resumeRepository;
    private final AiService aiService;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public ResumeController(ResumeRepository resumeRepository, AiService aiService) {
        this.resumeRepository = resumeRepository;
        this.aiService = aiService;
    }

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal UserPrincipal p) {
        if (p == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
        return resumeRepository.findTopByUserIdOrderByUploadedAtDesc(p.getUserId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@AuthenticationPrincipal UserPrincipal p,
                                    @RequestParam("file") MultipartFile file) {
        if (p == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            // Save file with absolute safe path
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String cleanName = (originalFilename != null && !originalFilename.isBlank())
                    ? Paths.get(originalFilename).getFileName().toString()
                    : "resume.pdf";

            String savedFileName = UUID.randomUUID() + "_" + cleanName;
            Path filePath = uploadPath.resolve(savedFileName);

            try (InputStream in = file.getInputStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Extract text from document (.pdf, .docx, .txt)
            String extractedText = "";
            String lowerName = cleanName.toLowerCase();
            if (lowerName.endsWith(".pdf")) {
                try (PDDocument doc = Loader.loadPDF(filePath.toFile())) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    extractedText = stripper.getText(doc);
                } catch (Exception ex) {
                    System.err.println("Warning: could not extract text from PDF: " + ex.getMessage());
                }
            } else if (lowerName.endsWith(".docx")) {
                extractedText = extractDocxText(filePath.toFile());
            } else if (lowerName.endsWith(".txt")) {
                try {
                    extractedText = Files.readString(filePath, StandardCharsets.UTF_8);
                } catch (Exception ignored) {
                }
            }

            // Accurate skill extraction with word boundary pattern matching
            List<String> skills = extractSkills(extractedText);

            Resume resume = new Resume();
            resume.setUserId(p.getUserId());
            resume.setFileName(cleanName);
            resume.setFileUrl("/uploads/" + savedFileName);
            resume.setExtractedText(extractedText);
            resume.setExtractedSkills(skills);
            resume.setUploadedAt(LocalDateTime.now());

            return ResponseEntity.ok(resumeRepository.save(resume));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload resume: " + e.getMessage());
        }
    }

    private String extractDocxText(File file) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if ("word/document.xml".equals(entry.getName())) {
                    String xml = new String(zis.readAllBytes(), StandardCharsets.UTF_8);
                    return xml.replaceAll("<[^>]+>", " ").replaceAll("\\s+", " ").trim();
                }
            }
        } catch (Exception e) {
            System.err.println("DOCX extraction error: " + e.getMessage());
        }
        return "";
    }

    private List<String> extractSkills(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }

        // Skills mapped to their accurate regex word-boundary patterns
        Map<String, String> skillPatterns = new LinkedHashMap<>();
        skillPatterns.put("Java", "\\bJava\\b");
        skillPatterns.put("Python", "\\bPython\\b");
        skillPatterns.put("JavaScript", "\\b(?:JavaScript|JS)\\b");
        skillPatterns.put("TypeScript", "\\b(?:TypeScript|TS)\\b");
        skillPatterns.put("C++", "(?:^|[\\s,;/()|])C\\+\\+(?:$|[\\s,;/()|])");
        skillPatterns.put("C", "(?:^|[\\s,;/()|])C(?:$|[\\s,;/()|])");
        skillPatterns.put("Go", "\\b(?:Go|Golang)\\b");
        skillPatterns.put("Rust", "\\bRust\\b");
        skillPatterns.put("Spring Boot", "\\bSpring\\s+Boot\\b");
        skillPatterns.put("Spring", "\\bSpring\\b");
        skillPatterns.put("React", "\\bReact(?:\\.js)?\\b");
        skillPatterns.put("Angular", "\\bAngular(?:\\.js)?\\b");
        skillPatterns.put("Vue", "\\bVue(?:\\.js)?\\b");
        skillPatterns.put("Node.js", "\\bNode(?:\\.js)?\\b");
        skillPatterns.put("Express", "\\bExpress(?:\\.js)?\\b");
        skillPatterns.put("MongoDB", "\\bMongo(?:DB)?\\b");
        skillPatterns.put("MySQL", "\\bMySQL\\b");
        skillPatterns.put("PostgreSQL", "\\b(?:PostgreSQL|Postgres)\\b");
        skillPatterns.put("Redis", "\\bRedis\\b");
        skillPatterns.put("Firebase", "\\bFirebase\\b");
        skillPatterns.put("AWS", "\\b(?:AWS|Amazon Web Services)\\b");
        skillPatterns.put("Azure", "\\bAzure\\b");
        skillPatterns.put("GCP", "\\b(?:GCP|Google Cloud)\\b");
        skillPatterns.put("Docker", "\\bDocker\\b");
        skillPatterns.put("Kubernetes", "\\b(?:Kubernetes|K8s)\\b");
        skillPatterns.put("Jenkins", "\\bJenkins\\b");
        skillPatterns.put("CI/CD", "\\bCI/CD\\b");
        skillPatterns.put("Git", "\\bGit\\b");
        skillPatterns.put("GitHub", "\\bGitHub\\b");
        skillPatterns.put("GitLab", "\\bGitLab\\b");
        skillPatterns.put("REST API", "\\bREST(?:ful)?(?:\\s+API)?\\b");
        skillPatterns.put("GraphQL", "\\bGraphQL\\b");
        skillPatterns.put("Microservices", "\\bMicroservices\\b");
        skillPatterns.put("HTML", "\\bHTML5?\\b");
        skillPatterns.put("CSS", "\\bCSS3?\\b");
        skillPatterns.put("Tailwind", "\\bTailwind(?:CSS)?\\b");
        skillPatterns.put("Bootstrap", "\\bBootstrap\\b");
        skillPatterns.put("Machine Learning", "\\bMachine\\s+Learning\\b");
        skillPatterns.put("Deep Learning", "\\bDeep\\s+Learning\\b");
        skillPatterns.put("TensorFlow", "\\bTensorFlow\\b");
        skillPatterns.put("PyTorch", "\\bPyTorch\\b");
        skillPatterns.put("DSA", "\\b(?:DSA|Data Structures|Algorithms)\\b");
        skillPatterns.put("System Design", "\\bSystem\\s+Design\\b");
        skillPatterns.put("SQL", "\\bSQL\\b");
        skillPatterns.put("Linux", "\\bLinux\\b");

        List<String> found = new ArrayList<>();
        for (Map.Entry<String, String> entry : skillPatterns.entrySet()) {
            Pattern p;
            if (entry.getKey().equals("C")) {
                // Case sensitive for single letter C to avoid matching random lowercase c
                p = Pattern.compile(entry.getValue());
            } else {
                p = Pattern.compile(entry.getValue(), Pattern.CASE_INSENSITIVE);
            }
            if (p.matcher(text).find()) {
                found.add(entry.getKey());
            }
        }
        return found;
    }
}
