import { Link } from 'react-router-dom'
import {
  TrendingUp, Code2, FileText, Zap, Bot,
  Briefcase, Target, Globe, ArrowRight, CheckCircle2
} from 'lucide-react'
import { Github } from '../components/Icons'

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
    <div className="min-h-screen bg-white font-sans">
      {/* Navbar */}
      <nav className="sticky top-0 z-50 bg-white/80 backdrop-blur border-b border-slate-100">
        <div className="max-w-6xl mx-auto px-6 h-16 flex items-center justify-between">
          <div className="flex items-center gap-2.5">
            <div className="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center">
              <TrendingUp size={16} className="text-white" />
            </div>
            <span className="font-bold text-slate-900 text-[15px]">Career Sync</span>
          </div>
          <div className="flex items-center gap-3">
            <Link to="/auth" className="text-sm text-slate-600 hover:text-slate-900 font-medium px-3 py-1.5">
              Sign In
            </Link>
            <Link to="/auth?mode=signup" className="btn btn-primary btn-sm">
              Get Started
            </Link>
          </div>
        </div>
      </nav>

      {/* Hero */}
      <section className="relative overflow-hidden">
        {/* Background gradient */}
        <div className="absolute inset-0 bg-gradient-to-br from-blue-50 via-white to-slate-50 pointer-events-none" />
        <div className="absolute top-0 right-0 w-[600px] h-[600px] bg-blue-100/40 rounded-full blur-3xl -translate-y-1/2 translate-x-1/3 pointer-events-none" />

        <div className="relative max-w-6xl mx-auto px-6 pt-20 pb-24 text-center">
          <div className="inline-flex items-center gap-2 bg-blue-50 text-blue-700 text-xs font-semibold px-4 py-1.5 rounded-full mb-6 border border-blue-100">
            <Bot size={12} />
            Powered by Gemini AI · Built for Students
          </div>

          <h1 className="text-5xl md:text-6xl font-bold text-slate-900 leading-[1.1] mb-6 tracking-tight">
            Your AI-Powered<br />
            <span className="text-blue-600">Career Command Center</span>
          </h1>

          <p className="text-lg text-slate-500 max-w-2xl mx-auto mb-10 leading-relaxed">
            Manage your resume, skills, GitHub, LeetCode, projects, applications, and goals — 
            all in one place. Let AI identify your gaps and guide your career journey.
          </p>

          <div className="flex flex-col sm:flex-row items-center justify-center gap-4 mb-16">
            <Link to="/auth?mode=signup" className="btn btn-primary btn-lg gap-2">
              Start for Free <ArrowRight size={18} />
            </Link>
            <Link to="/auth" className="btn btn-secondary btn-lg">
              Sign In
            </Link>
          </div>

          {/* Stats row */}
          <div className="flex flex-wrap justify-center gap-8">
            {stats.map((s) => (
              <div key={s.label} className="text-center">
                <div className="text-2xl font-bold text-blue-600">{s.value}</div>
                <div className="text-xs text-slate-500 font-medium mt-0.5">{s.label}</div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Problem → Solution */}
      <section className="py-20 bg-slate-50">
        <div className="max-w-6xl mx-auto px-6">
          <div className="grid md:grid-cols-2 gap-12 items-center">
            <div>
              <div className="inline-flex items-center gap-2 bg-red-50 text-red-600 text-xs font-semibold px-3 py-1.5 rounded-full mb-5 border border-red-100">
                The Problem
              </div>
              <h2 className="text-3xl font-bold text-slate-900 mb-5">
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
                  <div key={p} className="flex items-center gap-3 text-slate-600 text-sm">
                    <div className="w-5 h-5 rounded-full bg-red-100 flex items-center justify-center flex-shrink-0">
                      <span className="text-red-500 text-xs">✗</span>
                    </div>
                    {p}
                  </div>
                ))}
              </div>
            </div>
            <div>
              <div className="inline-flex items-center gap-2 bg-green-50 text-green-600 text-xs font-semibold px-3 py-1.5 rounded-full mb-5 border border-green-100">
                Career Sync Solution
              </div>
              <h2 className="text-3xl font-bold text-slate-900 mb-5">
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
                  <div key={s} className="flex items-center gap-3 text-slate-600 text-sm">
                    <CheckCircle2 size={18} className="text-green-500 flex-shrink-0" />
                    {s}
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Features Grid */}
      <section className="py-24">
        <div className="max-w-6xl mx-auto px-6">
          <div className="text-center mb-14">
            <h2 className="text-3xl font-bold text-slate-900 mb-3">
              Everything you need to accelerate your career
            </h2>
            <p className="text-slate-500 text-lg max-w-xl mx-auto">
              8 powerful modules built for students and job seekers, all powered by Gemini AI.
            </p>
          </div>
          <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-5">
            {features.map(({ icon: Icon, title, desc }) => (
              <div key={title} className="card p-5 group">
                <div className="w-10 h-10 bg-blue-50 rounded-xl flex items-center justify-center mb-4 group-hover:bg-blue-100 transition-colors">
                  <Icon size={20} className="text-blue-600" />
                </div>
                <h3 className="text-sm font-semibold text-slate-900 mb-2">{title}</h3>
                <p className="text-xs text-slate-500 leading-relaxed">{desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* How It Works */}
      <section className="py-20 bg-slate-50">
        <div className="max-w-5xl mx-auto px-6">
          <div className="text-center mb-14">
            <h2 className="text-3xl font-bold text-slate-900 mb-3">How it works</h2>
            <p className="text-slate-500">Get started in minutes, see results immediately.</p>
          </div>
          <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {steps.map(({ n, title, desc }) => (
              <div key={n} className="text-center">
                <div className="w-12 h-12 bg-blue-600 rounded-xl flex items-center justify-center mx-auto mb-4 text-white font-bold text-sm">
                  {n}
                </div>
                <h3 className="text-sm font-semibold text-slate-900 mb-2">{title}</h3>
                <p className="text-xs text-slate-500 leading-relaxed">{desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* CTA */}
      <section className="py-24 bg-blue-600">
        <div className="max-w-3xl mx-auto px-6 text-center">
          <h2 className="text-3xl font-bold text-white mb-4">
            Ready to take control of your career?
          </h2>
          <p className="text-blue-100 mb-8 text-lg">
            Join Career Sync and get AI-powered career guidance tailored to your goals.
          </p>
          <Link to="/auth?mode=signup" className="inline-flex items-center gap-2 bg-white text-blue-600 font-semibold px-8 py-3.5 rounded-xl hover:bg-blue-50 transition-colors text-sm">
            Get Started Free <ArrowRight size={16} />
          </Link>
        </div>
      </section>

      {/* Footer */}
      <footer className="py-8 border-t border-slate-100">
        <div className="max-w-6xl mx-auto px-6 flex flex-col sm:flex-row items-center justify-between gap-4">
          <div className="flex items-center gap-2">
            <div className="w-6 h-6 bg-blue-600 rounded-md flex items-center justify-center">
              <TrendingUp size={12} className="text-white" />
            </div>
            <span className="text-sm font-semibold text-slate-700">Career Sync</span>
          </div>
          <p className="text-xs text-slate-400">
            Built with React + Spring Boot + Gemini AI · College Project
          </p>
        </div>
      </footer>
    </div>
  )
}
