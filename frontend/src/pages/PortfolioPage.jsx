import { useEffect, useState } from 'react'
import { profileApi, skillsApi, projectsApi, certificatesApi, githubApi } from '../services/api'
import { useAuth } from '../context/AuthContext'
import {
  Globe, ExternalLink, Award,
  FolderOpen, Zap, Download, Share2, TrendingUp
} from 'lucide-react'
import { Github, Linkedin } from '../components/Icons'

export default function PortfolioPage() {
  const { user } = useAuth()
  const [data, setData] = useState({ profile: null, skills: [], projects: [], certs: [], github: null })
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    Promise.all([
      profileApi.get().catch(() => ({ data: null })),
      skillsApi.getAll().catch(() => ({ data: [] })),
      projectsApi.getAll().catch(() => ({ data: [] })),
      certificatesApi.getAll().catch(() => ({ data: [] })),
      githubApi.get().catch(() => ({ data: null })),
    ]).then(([pRes, sRes, projRes, cRes, ghRes]) => {
      setData({
        profile: pRes.data,
        skills: sRes.data || [],
        projects: projRes.data || [],
        certs: cRes.data || [],
        github: ghRes.data,
      })
    }).finally(() => setLoading(false))
  }, [])

  const { profile, skills, projects, certs, github } = data

  // Group skills by category
  const skillGroups = skills.reduce((acc, s) => {
    const cat = s.category || 'Other'
    if (!acc[cat]) acc[cat] = []
    acc[cat].push(s)
    return acc
  }, {})

  const handlePrint = () => window.print()

  if (loading) return (
    <div className="space-y-4">
      {[...Array(4)].map((_, i) => <div key={i} className="skeleton h-32 rounded-xl" />)}
    </div>
  )

  return (
    <div className="space-y-5 animate-fade-in">
      {/* Toolbar */}
      <div className="flex items-center justify-between">
        <div>
          <h2 className="page-title">Portfolio</h2>
          <p className="page-subtitle">Auto-generated from your Career Sync profile</p>
        </div>
        <div className="flex gap-2">
          <button onClick={handlePrint} className="btn btn-secondary gap-2">
            <Download size={15} /> Export PDF
          </button>
          <button
            onClick={() => { navigator.clipboard.writeText(window.location.href); }}
            className="btn btn-primary gap-2"
          >
            <Share2 size={15} /> Share
          </button>
        </div>
      </div>

      {/* Portfolio Card */}
      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-hidden" id="portfolio-preview">

        {/* Hero banner */}
        <div className="bg-gradient-to-r from-blue-600 to-blue-800 px-8 py-12 text-white">
          <div className="flex items-start gap-6">
            <div className="w-20 h-20 rounded-2xl bg-white/20 flex items-center justify-center text-3xl font-bold border-2 border-white/30 flex-shrink-0">
              {(profile?.name || user?.email || 'U')[0].toUpperCase()}
            </div>
            <div className="flex-1">
              <h1 className="text-3xl font-bold mb-1">{profile?.name || 'Your Name'}</h1>
              {profile?.targetRole && (
                <p className="text-blue-100 text-lg font-medium mb-3">{profile.targetRole}</p>
              )}
              {profile?.bio && <p className="text-blue-100 text-sm mb-4 max-w-lg">{profile.bio}</p>}
              <div className="flex flex-wrap gap-3">
                {profile?.email && (
                  <span className="flex items-center gap-1.5 text-sm bg-white/10 px-3 py-1.5 rounded-lg">
                    📧 {profile.email}
                  </span>
                )}
                {profile?.location && (
                  <span className="flex items-center gap-1.5 text-sm bg-white/10 px-3 py-1.5 rounded-lg">
                    📍 {profile.location}
                  </span>
                )}
                {profile?.linkedinUrl && (
                  <a href={profile.linkedinUrl} target="_blank" rel="noopener noreferrer"
                    className="flex items-center gap-1.5 text-sm bg-white/10 px-3 py-1.5 rounded-lg hover:bg-white/20 transition-colors">
                    <Linkedin size={14} /> LinkedIn
                  </a>
                )}
                {github?.username && (
                  <a href={`https://github.com/${github.username}`} target="_blank" rel="noopener noreferrer"
                    className="flex items-center gap-1.5 text-sm bg-white/10 px-3 py-1.5 rounded-lg hover:bg-white/20 transition-colors">
                    <Github size={14} /> GitHub
                  </a>
                )}
              </div>
            </div>
          </div>
        </div>

        <div className="p-8 space-y-10">

          {/* Stats row */}
          {(github || skills.length > 0 || projects.length > 0) && (
            <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
              <div className="text-center p-4 bg-slate-50 rounded-xl">
                <p className="text-2xl font-bold text-blue-600">{skills.length}</p>
                <p className="text-xs text-slate-500 font-medium mt-1">Skills</p>
              </div>
              <div className="text-center p-4 bg-slate-50 rounded-xl">
                <p className="text-2xl font-bold text-purple-600">{projects.length}</p>
                <p className="text-xs text-slate-500 font-medium mt-1">Projects</p>
              </div>
              <div className="text-center p-4 bg-slate-50 rounded-xl">
                <p className="text-2xl font-bold text-amber-600">{certs.length}</p>
                <p className="text-xs text-slate-500 font-medium mt-1">Certificates</p>
              </div>
              <div className="text-center p-4 bg-slate-50 rounded-xl">
                <p className="text-2xl font-bold text-green-600">{github?.repositories || 0}</p>
                <p className="text-xs text-slate-500 font-medium mt-1">GitHub Repos</p>
              </div>
            </div>
          )}

          {/* Education */}
          {profile?.education && (
            <section>
              <h2 className="text-lg font-bold text-slate-900 mb-4 flex items-center gap-2">
                🎓 Education
              </h2>
              <div className="bg-slate-50 rounded-xl p-4">
                <p className="text-sm font-semibold text-slate-800">{profile.education}</p>
              </div>
            </section>
          )}

          {/* Skills */}
          {skills.length > 0 && (
            <section>
              <h2 className="text-lg font-bold text-slate-900 mb-4 flex items-center gap-2">
                <Zap size={18} className="text-blue-600" /> Technical Skills
              </h2>
              <div className="space-y-4">
                {Object.entries(skillGroups).map(([cat, catSkills]) => (
                  <div key={cat}>
                    <h3 className="text-xs font-semibold text-slate-500 uppercase tracking-wide mb-2">{cat}</h3>
                    <div className="flex flex-wrap gap-2">
                      {catSkills.map(s => (
                        <div key={s.id} className="flex items-center gap-2 bg-slate-50 border border-slate-100 rounded-lg px-3 py-1.5">
                          <span className="text-sm font-medium text-slate-700">{s.skillName}</span>
                          <div className="w-12 h-1.5 bg-slate-200 rounded-full overflow-hidden">
                            <div
                              className="h-full bg-blue-500 rounded-full"
                              style={{ width: `${s.proficiency || 0}%` }}
                            />
                          </div>
                          <span className="text-xs text-slate-400">{s.proficiency}%</span>
                        </div>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            </section>
          )}

          {/* Projects */}
          {projects.length > 0 && (
            <section>
              <h2 className="text-lg font-bold text-slate-900 mb-4 flex items-center gap-2">
                <FolderOpen size={18} className="text-purple-600" /> Projects
              </h2>
              <div className="grid sm:grid-cols-2 gap-4">
                {projects.map(p => (
                  <div key={p.id} className="border border-slate-100 rounded-xl p-4 hover:border-blue-200 transition-colors">
                    <h3 className="text-sm font-bold text-slate-900 mb-1">{p.name}</h3>
                    {p.role && <p className="text-xs text-blue-600 font-medium mb-2">{p.role}</p>}
                    {p.description && <p className="text-xs text-slate-500 mb-3 leading-relaxed">{p.description}</p>}
                    {p.technologies?.length > 0 && (
                      <div className="flex flex-wrap gap-1.5 mb-3">
                        {p.technologies.map(t => (
                          <span key={t} className="badge badge-blue text-[11px]">{t}</span>
                        ))}
                      </div>
                    )}
                    <div className="flex gap-2">
                      {p.githubUrl && (
                        <a href={p.githubUrl} target="_blank" rel="noopener noreferrer"
                          className="text-xs flex items-center gap-1 text-slate-500 hover:text-blue-600 transition-colors">
                          <Github size={12} /> Code
                        </a>
                      )}
                      {p.liveUrl && (
                        <a href={p.liveUrl} target="_blank" rel="noopener noreferrer"
                          className="text-xs flex items-center gap-1 text-slate-500 hover:text-blue-600 transition-colors">
                          <ExternalLink size={12} /> Live Demo
                        </a>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            </section>
          )}

          {/* Certificates */}
          {certs.length > 0 && (
            <section>
              <h2 className="text-lg font-bold text-slate-900 mb-4 flex items-center gap-2">
                <Award size={18} className="text-amber-500" /> Certifications
              </h2>
              <div className="grid sm:grid-cols-2 gap-3">
                {certs.map(c => (
                  <div key={c.id} className="flex items-start gap-3 border border-slate-100 rounded-xl p-4">
                    <div className="w-9 h-9 bg-amber-50 rounded-lg flex items-center justify-center flex-shrink-0">
                      <Award size={16} className="text-amber-500" />
                    </div>
                    <div className="flex-1 min-w-0">
                      <h3 className="text-sm font-semibold text-slate-800 truncate">{c.title}</h3>
                      <p className="text-xs text-slate-500">{c.issuer}</p>
                      {c.issueDate && (
                        <p className="text-xs text-slate-400 mt-0.5">
                          {new Date(c.issueDate).toLocaleDateString('en-IN', { year: 'numeric', month: 'long' })}
                        </p>
                      )}
                    </div>
                    {c.credentialUrl && (
                      <a href={c.credentialUrl} target="_blank" rel="noopener noreferrer"
                        className="text-blue-600 hover:text-blue-700 flex-shrink-0">
                        <ExternalLink size={14} />
                      </a>
                    )}
                  </div>
                ))}
              </div>
            </section>
          )}

          {/* GitHub Stats */}
          {github && (
            <section>
              <h2 className="text-lg font-bold text-slate-900 mb-4 flex items-center gap-2">
                <Github size={18} /> GitHub Activity
              </h2>
              <div className="grid grid-cols-3 gap-4 mb-4">
                <div className="text-center bg-slate-50 rounded-xl p-3">
                  <p className="text-xl font-bold text-slate-900">{github.repositories}</p>
                  <p className="text-xs text-slate-500">Repositories</p>
                </div>
                <div className="text-center bg-slate-50 rounded-xl p-3">
                  <p className="text-xl font-bold text-slate-900">{github.followers}</p>
                  <p className="text-xs text-slate-500">Followers</p>
                </div>
                <div className="text-center bg-slate-50 rounded-xl p-3">
                  <p className="text-xl font-bold text-slate-900">{github.following}</p>
                  <p className="text-xs text-slate-500">Following</p>
                </div>
              </div>
              {github.languages && Object.keys(github.languages).length > 0 && (
                <div className="flex flex-wrap gap-2">
                  {Object.entries(github.languages).sort(([, a], [, b]) => b - a).map(([lang]) => (
                    <span key={lang} className="badge badge-blue">{lang}</span>
                  ))}
                </div>
              )}
            </section>
          )}

          {/* Empty state */}
          {skills.length === 0 && projects.length === 0 && certs.length === 0 && (
            <div className="text-center py-12">
              <Globe size={48} className="text-slate-200 mx-auto mb-4" />
              <h3 className="text-slate-500 font-medium mb-2">Your portfolio is empty</h3>
              <p className="text-sm text-slate-400">
                Add skills, projects, and certificates to generate your portfolio.
              </p>
            </div>
          )}

          {/* Footer */}
          <div className="border-t border-slate-100 pt-6 flex items-center justify-between">
            <div className="flex items-center gap-2">
              <div className="w-6 h-6 bg-blue-600 rounded-md flex items-center justify-center">
                <TrendingUp size={12} className="text-white" />
              </div>
              <span className="text-xs text-slate-400">Generated with Career Sync</span>
            </div>
            <span className="text-xs text-slate-400">{new Date().toLocaleDateString()}</span>
          </div>
        </div>
      </div>
    </div>
  )
}
