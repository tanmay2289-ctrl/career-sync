import { NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import {
  LayoutDashboard, User, FileText, Zap, Code2,
  FolderOpen, Award, Briefcase, Target, Bot, Globe,
  LogOut, X, TrendingUp
} from 'lucide-react'
import { Github, Linkedin } from './Icons'

const navItems = [
  { to: '/dashboard',    icon: LayoutDashboard, label: 'Dashboard' },
  { to: '/profile',      icon: User,            label: 'Profile' },
  { to: '/resume',       icon: FileText,        label: 'Resume' },
  { to: '/skills',       icon: Zap,             label: 'Skills' },
  { to: '/github',       icon: Github,          label: 'GitHub' },
  { to: '/leetcode',     icon: Code2,           label: 'LeetCode' },
  { to: '/linkedin',     icon: Linkedin,        label: 'LinkedIn' },
  { to: '/projects',     icon: FolderOpen,      label: 'Projects' },
  { to: '/certificates', icon: Award,           label: 'Certificates' },
  { to: '/jobs',         icon: Briefcase,       label: 'Jobs & Internships' },
  { to: '/goals',        icon: Target,          label: 'Goals' },
  { to: '/ai-assistant', icon: Bot,             label: 'AI Assistant' },
  { to: '/portfolio',    icon: Globe,           label: 'Portfolio' },
]

export default function Sidebar({ onClose }) {
  const { signOut, user } = useAuth()
  const navigate = useNavigate()

  const handleSignOut = async () => {
    await signOut()
    navigate('/')
  }

  return (
    <div className="flex flex-col h-full">
      {/* Logo */}
      <div className="flex items-center justify-between p-5 border-b border-slate-100">
        <div className="flex items-center gap-2.5">
          <div className="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center">
            <TrendingUp size={16} className="text-white" />
          </div>
          <span className="font-700 text-slate-900 text-[15px] font-bold">Career Sync</span>
        </div>
        <button onClick={onClose} className="lg:hidden p-1 rounded-md hover:bg-slate-100 text-slate-500">
          <X size={16} />
        </button>
      </div>

      {/* User info */}
      <div className="px-4 py-3 border-b border-slate-50">
        <div className="flex items-center gap-3">
          <div className="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 text-sm font-600 font-semibold">
            {user?.email?.[0]?.toUpperCase() || 'U'}
          </div>
          <div className="min-w-0">
            <p className="text-xs font-semibold text-slate-700 truncate">{user?.email}</p>
          </div>
        </div>
      </div>

      {/* Nav */}
      <nav className="flex-1 px-3 py-3 space-y-0.5 overflow-y-auto">
        {navItems.map(({ to, icon: Icon, label }) => (
          <NavLink
            key={to}
            to={to}
            onClick={onClose}
            className={({ isActive }) =>
              `sidebar-item ${isActive ? 'active' : ''}`
            }
          >
            <Icon size={16} />
            <span>{label}</span>
          </NavLink>
        ))}
      </nav>

      {/* Logout */}
      <div className="p-3 border-t border-slate-100">
        <button
          onClick={handleSignOut}
          className="sidebar-item w-full text-red-500 hover:bg-red-50 hover:text-red-600"
        >
          <LogOut size={16} />
          <span>Sign Out</span>
        </button>
      </div>
    </div>
  )
}
