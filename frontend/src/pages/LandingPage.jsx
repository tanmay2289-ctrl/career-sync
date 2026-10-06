import { Suspense, lazy } from 'react'
import { Link } from 'react-router-dom'
import {
  TrendingUp, Code2, FileText, Zap, Bot,
  Briefcase, Target, Globe, ArrowRight, CheckCircle2, Sparkles
} from 'lucide-react'
import { Github } from '../components/Icons'

const Spline = lazy(() => import('@splinetool/react-spline'))

const features = [
  { icon: FileText,  title: 'Smart Resume Analysis',   desc: 'Upload your resume and get AI-powered insights, skill extraction, and improvement recommendations instantly.' },
  { icon: Zap,       title: 'Skill Gap Detection',     desc: 'Paste any job description and instantly see which skills you have, which you\'re missing, and a personalized learning roadmap.' },
  { icon: Github,    title: 'GitHub Integration',      desc: 'Connect your GitHub to showcase repositories, languages, contribution stats, and activity on your career dashboard.' },
  { icon: Code2,     title: 'LeetCode Tracking',       desc: 'Track your DSA progress with easy, medium, and hard problem counts and visualize your coding growth over time.' },
  { icon: Bot,       title: 'AI Career Assistant',     desc: 'Ask anything — "Am I ready for an SDE internship?", "What should I learn next?" Get answers tailored to your profile.' },
  { icon: Briefcase, title: 'Job Application Tracker', desc: 'Track every application from Saved → Applied → Interview → Selected. Never lose track of an opportunity again.' },
  { icon: Target,    title: 'Goal Tracking',           desc: 'Set DSA, project, certification, and placement goals with deadlines. Visualize progress and stay on track.' },
  { icon: Globe,     title: 'Portfolio Generator',     desc: 'Auto-generate a shareable portfolio from your profile, skills, projects, and certificates — no coding required.' },
]

const stats = [
  { value: '14+', label: 'Career Modules' },
  { value: 'AI', label: 'Powered by Gemini' },
  { value: '100%', label: 'Student Focused' },
  { value: 'Free', label: 'College Project' },
]

const steps = [
  { n: '01', title: 'Create Your Profile', desc: 'Add your education, target role, LinkedIn, GitHub, and LeetCode.' },
  { n: '02', title: 'Upload Your Resume', desc: 'AI extracts skills, projects, and education automatically.' },
  { n: '03', title: 'Get AI Analysis', desc: 'Gemini analyzes your profile and identifies skill gaps.' },
  { n: '04', title: 'Track & Improve', desc: 'Monitor applications, goals, and career progress in one place.' },
]

export default function LandingPage() {
  return (
    <div className="min-h-screen bg-[#0a0a0f] font-sans text-white overflow-x-hidden">
      {/* ── Navbar ── */}
      <nav className="fixed top-0 left-0 right-0 z-50 border-b border-white/5"
        style={{ background: 'rgba(10,10,15,0.7)', backdropFilter: 'blur(20px)' }}>
        <div className="max-w-7xl mx-auto px-6 h-16 flex items-center justify-between">
          <div className="flex items-center gap-2.5">
            <div className="w-8 h-8 rounded-lg flex items-center justify-center"
              style={{ background: 'linear-gradient(135deg,#6366f1,#8b5cf6)' }}>
              <TrendingUp size={16} className="text-white" />
            </div>
            <span className="font-bold text-white text-[15px] tracking-tight">Career Sync</span>
          </div>
          <div className="flex items-center gap-3">
            <Link to="/auth"
              className="text-sm text-white/60 hover:text-white transition-colors font-medium px-3 py-1.5">
              Sign In
            </Link>
            <Link to="/auth?mode=signup"
              className="text-sm font-semibold px-4 py-2 rounded-xl transition-all text-white"
              style={{ background: 'linear-gradient(135deg,#6366f1,#8b5cf6)' }}>
              Get Started
            </Link>
          </div>
        </div>
      </nav>

      {/* ── Hero with Spline ── */}
      <section className="relative min-h-screen flex flex-col items-center justify-center pt-16 overflow-hidden">
        {/* Spline 3D background */}
        <div className="absolute inset-0 z-0">
          <Suspense fallback={
            <div className="w-full h-full flex items-center justify-center">
              <div className="w-10 h-10 border-2 border-indigo-500 border-t-transparent rounded-full animate-spin" />
            </div>
          }>
            <Spline
              scene="https://prod.spline.design/1e75aeea-f141-4b1e-a7ff-5f305a470b23/scene.splinecode"
              style={{ width: '100%', height: '100%' }}
            />
          </Suspense>
        </div>

        {/* Gradient overlay so text stays readable */}
        <div className="absolute inset-0 z-10 pointer-events-none"
          style={{ background: 'linear-gradient(to bottom, rgba(10,10,15,0.3) 0%, rgba(10,10,15,0.15) 50%, rgba(10,10,15,0.85) 100%)' }} />

        {/* Hero content */}
        <div className="relative z-20 max-w-4xl mx-auto px-6 text-center">
          <div className="inline-flex items-center gap-2 px-4 py-1.5 rounded-full text-xs font-semibold mb-6 border border-indigo-500/30"
            style={{ background: 'rgba(99,102,241,0.15)', color: '#a5b4fc' }}>
            <Sparkles size={12} />
            Powered by Gemini AI · Built for Students
          </div>

          <h1 className="text-5xl md:text-7xl font-extrabold leading-[1.05] mb-6 tracking-tight">
            Your AI-Powered<br />
            <span style={{ background: 'linear-gradient(135deg,#6366f1,#a78bfa,#38bdf8)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
              Career Command Center
            </span>
          </h1>

          <p className="text-lg md:text-xl text-white/60 max-w-2xl mx-auto mb-10 leading-relaxed">
            Manage your resume, skills, GitHub, LeetCode, projects, applications, and goals —
            all in one place. Let AI identify your gaps and guide your journey.
          </p>

          <div className="flex flex-col sm:flex-row items-center justify-center gap-4 mb-20">
            <Link to="/auth?mode=signup"
              className="flex items-center gap-2 text-white font-semibold px-8 py-3.5 rounded-xl transition-all hover:opacity-90 hover:scale-[1.02] text-sm shadow-lg"
              style={{ background: 'linear-gradient(135deg,#6366f1,#8b5cf6)', boxShadow: '0 0 30px rgba(99,102,241,0.4)' }}>
              Start for Free <ArrowRight size={17} />
            </Link>
            <Link to="/auth"
              className="flex items-center gap-2 text-white/80 font-semibold px-8 py-3.5 rounded-xl border border-white/10 hover:border-white/20 hover:bg-white/5 transition-all text-sm">
              Sign In
            </Link>
          </div>

          {/* Stats */}
          <div className="flex flex-wrap justify-center gap-10">
            {stats.map((s) => (
              <div key={s.label} className="text-center">
                <div className="text-3xl font-extrabold"
                  style={{ background: 'linear-gradient(135deg,#6366f1,#a78bfa)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
                  {s.value}
                </div>
                <div className="text-xs text-white/40 font-medium mt-1">{s.label}</div>
              </div>
            ))}
          </div>
        </div>

        {/* Scroll cue */}
        <div className="absolute bottom-8 left-1/2 -translate-x-1/2 z-20 flex flex-col items-center gap-2 animate-bounce">
          <div className="w-6 h-10 rounded-full border border-white/20 flex items-start justify-center pt-2">
            <div className="w-1 h-2.5 rounded-full bg-white/40" />
          </div>
        </div>
      </section>

      {/* ── Problem → Solution ── */}
      <section className="py-24 relative">
        <div className="absolute inset-0 pointer-events-none"
          style={{ background: 'radial-gradient(ellipse at 50% 0%, rgba(99,102,241,0.08) 0%, transparent 70%)' }} />
        <div className="max-w-6xl mx-auto px-6">
          <div className="grid md:grid-cols-2 gap-12 items-center">
            <div className="p-8 rounded-2xl border border-red-500/10"
              style={{ background: 'rgba(239,68,68,0.04)' }}>
              <div className="inline-flex items-center gap-2 bg-red-500/10 text-red-400 text-xs font-semibold px-3 py-1.5 rounded-full mb-5 border border-red-500/20">
                The Problem
              </div>
              <h2 className="text-2xl font-bold text-white mb-5">
                Career info scattered across too many platforms
              </h2>
              <div className="space-y-3">
                {[
                  'Resume on Google Drive, skills on LinkedIn',
                  'No idea which skills are missing for a target role',
                  'Lost track of 20+ job applications',
                  'GitHub and LeetCode progress not connected',
                  'No single view of career readiness',
                ].map((p) => (
                  <div key={p} className="flex items-center gap-3 text-white/50 text-sm">
                    <div className="w-5 h-5 rounded-full bg-red-500/10 border border-red-500/20 flex items-center justify-center flex-shrink-0">
                      <span className="text-red-400 text-xs">✗</span>
                    </div>
                    {p}
                  </div>
                ))}
              </div>
            </div>

            <div className="p-8 rounded-2xl border border-indigo-500/10"
              style={{ background: 'rgba(99,102,241,0.04)' }}>
              <div className="inline-flex items-center gap-2 bg-indigo-500/10 text-indigo-400 text-xs font-semibold px-3 py-1.5 rounded-full mb-5 border border-indigo-500/20">
                Career Sync Solution
              </div>
              <h2 className="text-2xl font-bold text-white mb-5">
                Everything in one intelligent platform
              </h2>
              <div className="space-y-3">
                {[
                  'Centralized career profile with all your information',
                  'AI skill-gap analysis against any job description',
                  'Visual job application pipeline tracker',
                  'GitHub + LeetCode stats on your dashboard',
                  'Career readiness score with actionable advice',
                ].map((s) => (
                  <div key={s} className="flex items-center gap-3 text-white/60 text-sm">
                    <CheckCircle2 size={18} className="text-indigo-400 flex-shrink-0" />
                    {s}
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* ── Features Grid ── */}
      <section className="py-24">
        <div className="max-w-6xl mx-auto px-6">
          <div className="text-center mb-14">
            <h2 className="text-3xl md:text-4xl font-extrabold text-white mb-3">
              Everything to accelerate your career
            </h2>
            <p className="text-white/40 text-lg max-w-xl mx-auto">
              8 powerful modules built for students, all powered by Gemini AI.
            </p>
          </div>
          <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-4">
            {features.map(({ icon: Icon, title, desc }) => (
              <div key={title}
                className="p-5 rounded-2xl border border-white/5 hover:border-indigo-500/30 transition-all group cursor-default"
                style={{ background: 'rgba(255,255,255,0.02)' }}>
                <div className="w-10 h-10 rounded-xl flex items-center justify-center mb-4 transition-all group-hover:scale-110"
                  style={{ background: 'rgba(99,102,241,0.15)' }}>
                  <Icon size={20} className="text-indigo-400" />
                </div>
                <h3 className="text-sm font-semibold text-white mb-2">{title}</h3>
                <p className="text-xs text-white/40 leading-relaxed">{desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* ── How It Works ── */}
      <section className="py-24 relative">
        <div className="absolute inset-0 pointer-events-none"
          style={{ background: 'radial-gradient(ellipse at 50% 50%, rgba(139,92,246,0.06) 0%, transparent 70%)' }} />
        <div className="max-w-5xl mx-auto px-6">
          <div className="text-center mb-14">
            <h2 className="text-3xl font-extrabold text-white mb-3">How it works</h2>
            <p className="text-white/40">Get started in minutes, see results immediately.</p>
          </div>
          <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {steps.map(({ n, title, desc }) => (
              <div key={n} className="text-center group">
                <div className="w-14 h-14 rounded-2xl flex items-center justify-center mx-auto mb-4 text-white font-extrabold text-sm transition-all group-hover:scale-110"
                  style={{ background: 'linear-gradient(135deg,#6366f1,#8b5cf6)', boxShadow: '0 0 20px rgba(99,102,241,0.3)' }}>
                  {n}
                </div>
                <h3 className="text-sm font-semibold text-white mb-2">{title}</h3>
                <p className="text-xs text-white/40 leading-relaxed">{desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* ── CTA ── */}
      <section className="py-28 relative overflow-hidden">
        <div className="absolute inset-0 pointer-events-none"
          style={{ background: 'linear-gradient(135deg, rgba(99,102,241,0.15) 0%, rgba(139,92,246,0.1) 100%)' }} />
        <div className="absolute inset-0 pointer-events-none border-t border-b border-indigo-500/10" />
        <div className="relative max-w-3xl mx-auto px-6 text-center">
          <h2 className="text-4xl md:text-5xl font-extrabold text-white mb-5">
            Ready to take control<br />of your career?
          </h2>
          <p className="text-white/50 mb-10 text-lg">
            Join Career Sync and get AI-powered career guidance tailored to your goals.
          </p>
          <Link to="/auth?mode=signup"
            className="inline-flex items-center gap-2 text-white font-semibold px-10 py-4 rounded-xl hover:opacity-90 hover:scale-[1.02] transition-all text-sm"
            style={{ background: 'linear-gradient(135deg,#6366f1,#8b5cf6)', boxShadow: '0 0 40px rgba(99,102,241,0.4)' }}>
            Get Started Free <ArrowRight size={17} />
          </Link>
        </div>
      </section>

      {/* ── Footer ── */}
      <footer className="py-8 border-t border-white/5">
        <div className="max-w-6xl mx-auto px-6 flex flex-col sm:flex-row items-center justify-between gap-4">
          <div className="flex items-center gap-2">
            <div className="w-6 h-6 rounded-md flex items-center justify-center"
              style={{ background: 'linear-gradient(135deg,#6366f1,#8b5cf6)' }}>
              <TrendingUp size={12} className="text-white" />
            </div>
            <span className="text-sm font-semibold text-white/70">Career Sync</span>
          </div>
          <p className="text-xs text-white/30">
            Built with React + Spring Boot + Gemini AI · College Project
          </p>
        </div>
      </footer>
    </div>
  )
}
