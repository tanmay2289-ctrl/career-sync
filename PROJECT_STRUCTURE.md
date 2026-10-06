# 📁 Career Sync — Project Architecture & File Structure

This document outlines the organized structure of the entire **Career Sync** full-stack application. It explains every directory, file, and its responsibility, as well as how the frontend, backend, database, and authentication services connect.

---

## 🌳 High-Level Directory Tree

```
Career Sync/
│
├── 📄 PROJECT_STRUCTURE.md       # Complete documentation of the project structure
├── 📄 pom.xml                    # Root Maven aggregator configuration
├── 📄 .gitignore                 # Root Git ignore rules (builds, node_modules, secrets)
├── 📁 .vscode/                   # Workspace settings for VS Code / Antigravity IDE
│   └── 📄 settings.json          # Clean file explorer settings (hides build clutter)
│
├── 📁 frontend/                  # React 19 + Vite + Tailwind CSS Client
│   ├── 📁 public/                # Static public assets (favicons, icons)
│   ├── 📁 src/                   # React source code
│   │   ├── 📁 components/        # Reusable UI components & layouts
│   │   │   ├── 📄 AppLayout.jsx  # Main application dashboard layout wrapper
│   │   │   ├── 📄 Sidebar.jsx    # Navigation sidebar with all app links
│   │   │   ├── 📄 TopBar.jsx     # Header bar displaying page title & breadcrumbs
│   │   │   └── 📄 Icons.jsx      # Custom SVG icons (GitHub, LinkedIn)
│   │   ├── 📁 pages/             # Route views and page screens (13 pages)
│   │   │   ├── 📄 LandingPage.jsx       # Public landing page with feature showcase
│   │   │   ├── 📄 AuthPage.jsx          # Login, Registration & Google OAuth
│   │   │   ├── 📄 Dashboard.jsx         # User overview, career metrics, & quick stats
│   │   │   ├── 📄 ProfilePage.jsx       # Personal info, education, target roles
│   │   │   ├── 📄 ResumePage.jsx        # PDF resume upload & AI text extraction
│   │   │   ├── 📄 SkillsPage.jsx        # Technical skills tracker & category breakdown
│   │   │   ├── 📄 GitHubPage.jsx        # GitHub profile sync, repos, & language stats
│   │   │   ├── 📄 LeetCodePage.jsx      # LeetCode stats, solved problems & rankings
│   │   │   ├── 📄 ProjectsPage.jsx      # Project showcase & tech stack management
│   │   │   ├── 📄 CertificatesPage.jsx  # Certification catalog & credentials
│   │   │   ├── 📄 JobsPage.jsx          # Job application tracker & status pipeline
│   │   │   ├── 📄 GoalsPage.jsx         # Career milestone & objective tracker
│   │   │   ├── 📄 AIAssistant.jsx       # Gemini AI interactive career coach chat
│   │   │   └── 📄 PortfolioPage.jsx     # Auto-generated sharable career portfolio
│   │   ├── 📁 context/           # Global React Context providers
│   │   │   └── 📄 AuthContext.jsx       # Supabase authentication session state
│   │   ├── 📁 services/          # External API & Backend communication
│   │   │   ├── 📄 api.js         # Axios HTTP client with Supabase JWT bearer interceptor
│   │   │   └── 📄 supabase.js    # Supabase client initialization
│   │   ├── 📄 App.jsx            # Main route configuration & protected route guard
│   │   ├── 📄 index.css          # Tailwind CSS directives & global styling rules
│   │   └── 📄 main.jsx           # React DOM root entrypoint
│   ├── 📄 index.html             # HTML entry template
│   ├── 📄 vite.config.js         # Vite build tool configuration
│   └── 📄 package.json           # Frontend dependencies & npm scripts
│
└── 📁 backend/                   # Spring Boot 3.2.5 + Java 17/21 REST API
    ├── 📄 pom.xml                # Maven build configuration & backend dependencies
    └── 📁 src/
        └── 📁 main/
            ├── 📁 resources/     # Application configuration & properties
            │   └── 📄 application.properties # Server port, MongoDB, Supabase, Gemini configs
            └── 📁 java/com/careersync/       # Java backend source code
                ├── 📄 CareerSyncApplication.java # Spring Boot main application entrypoint
                │
                ├── 📁 config/        # Application-level configuration
                │   └── 📄 SecurityConfig.java     # Spring Security filter chain & CORS rules
                │
                ├── 📁 security/      # Authentication & Token validation
                │   ├── 📄 SupabaseJwtFilter.java  # JWT interceptor validating Supabase tokens
                │   └── 📄 UserPrincipal.java      # Authenticated user representation
                │
                ├── 📁 model/         # MongoDB entity documents (@Document)
                │   ├── 📄 User.java               # User profile entity (`users` collection)
                │   ├── 📄 Resume.java             # Uploaded resume record (`resumes`)
                │   ├── 📄 Skill.java              # Technical skill record (`skills`)
                │   ├── 📄 Project.java            # Portfolio project record (`projects`)
                │   ├── 📄 Job.java                # Job application record (`jobs`)
                │   ├── 📄 Goal.java               # Career goal record (`goals`)
                │   ├── 📄 Certificate.java        # Certification record (`certificates`)
                │   ├── 📄 GitHubData.java         # Cached GitHub stats (`github_data`)
                │   ├── 📄 LeetCodeData.java       # Cached LeetCode stats (`leetcode_data`)
                │   └── 📄 AiAnalysis.java         # AI analysis cache (`ai_analysis`)
                │
                ├── 📁 repository/    # Spring Data MongoDB persistence layer
                │   ├── 📄 UserRepository.java
                │   ├── 📄 ResumeRepository.java
                │   ├── 📄 SkillRepository.java
                │   ├── 📄 ProjectRepository.java
                │   ├── 📄 JobRepository.java
                │   ├── 📄 GoalRepository.java
                │   ├── 📄 CertificateRepository.java
                │   ├── 📄 GitHubDataRepository.java
                │   ├── 📄 LeetCodeDataRepository.java
                │   └── 📄 AiAnalysisRepository.java
                │
                ├── 📁 service/       # Business logic layer
                │   ├── 📄 ProfileService.java     # User profile creation, update, & sync
                │   ├── 📄 SkillService.java       # Skills classification & gap analysis
                │   ├── 📄 GitHubService.java      # External GitHub API fetch & caching
                │   ├── 📄 LeetCodeService.java    # External LeetCode GraphQL fetch & caching
                │   └── 📄 AiService.java          # Google Gemini 2.0 Flash prompt processing
                │
                ├── 📁 controller/    # REST API endpoints (@RestController)
                │   ├── 📄 ProfileController.java     # `/api/profile`
                │   ├── 📄 ResumeController.java      # `/api/resume`
                │   ├── 📄 SkillController.java       # `/api/skills`
                │   ├── 📄 ProjectController.java     # `/api/projects`
                │   ├── 📄 JobController.java         # `/api/jobs`
                │   ├── 📄 GoalController.java        # `/api/goals`
                │   ├── 📄 CertificateController.java # `/api/certificates`
                │   ├── 📄 GitHubController.java      # `/api/github`
                │   ├── 📄 LeetCodeController.java    # `/api/leetcode`
                │   ├── 📄 AiController.java          # `/api/ai`
                │   └── 📄 AnalyticsController.java   # `/api/analytics`
                │
                └── 📁 dto/           # Data Transfer Objects (Request/Response schemas)
```

---

## 🔄 System Architecture & Data Flow

```
+-------------------------------------------------------------+
|                     React Frontend                          |
|             (Vite Dev Server @ Port 5173)                   |
+------------------------------+------------------------------+
                               |
            HTTP REST Requests | (with Supabase Bearer Token)
                               v
+-------------------------------------------------------------+
|                   Spring Boot Backend                       |
|               (Tomcat Server @ Port 8080)                   |
|                                                             |
|  [SupabaseJwtFilter] -> [Controllers] -> [Services]         |
+---------------+---------------------+-----------------------+
                |                     |
                v                     v
+-------------------------------+  +--------------------------+
|       MongoDB Database        |  |     External Services    |
|   (Local or MongoDB Atlas)    |  |  - Supabase (Auth / JWT) |
|  Stores Users, Resumes, Jobs, |  |  - Google Gemini 2.0 Flash|
|  Skills, Goals, Projects, etc.|  |  - GitHub API / LeetCode |
+-------------------------------+  +--------------------------+
```

---

## ⚙️ Key Configuration Files

| Purpose | File Path | Description |
| :--- | :--- | :--- |
| **Backend Config** | `backend/src/main/resources/application.properties` | Port, MongoDB URI, Supabase URL & keys, Gemini API key |
| **Frontend Config** | `frontend/.env` | Vite environment variables (`VITE_SUPABASE_URL`, `VITE_SUPABASE_ANON_KEY`, `VITE_API_URL`) |
| **Editor Settings** | `.vscode/settings.json` | Hides compiled `target` and `dist` build folders so source files are easy to view |
| **Git Exclusions** | `.gitignore` | Prevents secrets (`.env`), `node_modules`, and compiled build folders from being tracked |

---

## 🚀 Running the Project

### Running the Backend
From the root directory:
```powershell
cd backend
mvn spring-boot:run
```
*Backend URL:* `http://localhost:8080`

### Running the Frontend
From the root directory:
```powershell
cd frontend
npm run dev
```
*Frontend URL:* `http://localhost:5173`
