# 📱 TERMUX BUILD GUIDE - Cyber Beast Optimizer

## 🎯 Overview
Build APK Android langsung dari HP via GitHub Actions. Tidak perlu PC/Laptop!

## 📋 Prerequisites
- Android HP (Redmi 14C recommended)
- Termux dari F-Droid (bukan Play Store!)
- Koneksi internet
- Akun GitHub (gratis)

## 🚀 Quick Start (5 Menit)

### Step 1: Install Termux
```bash
# Download dari F-Droid:
# https://f-droid.org/packages/com.termux/
# ATAU
pkg install termux-api
```

### Step 2: Setup Environment
```bash
pkg update -y && pkg upgrade -y
pkg install -y git gh openssh curl wget
```

### Step 3: GitHub Auth
```bash
gh auth login

# Pilih:
# ? What account do you want to log into? GitHub.com
# ? What is your preferred protocol for Git operations on this host? HTTPS
# ? Authenticate Git with your GitHub credentials? Yes
# ? How would you like to authenticate GitHub CLI? Paste an authentication token
# 
# Buat token di: https://github.com/settings/tokens
# Scopes yang diperlukan: repo, workflow, read:org
```

### Step 4: Download & Extract Project
```bash
# Buat direktori kerja
mkdir -p ~/CyberBeastProjects
cd ~/CyberBeastProjects

# Download ZIP (ganti URL dengan link ZIP yang sesuai)
curl -L -o project.zip "https://your-download-link/Redmi14C-Cyber-Beast-Optimizer.zip"

# Extract
unzip project.zip
cd Redmi14C-Cyber-Beast-Optimizer
```

### Step 5: Push ke GitHub
```bash
# Init repo
git init
git add -A
git commit -m "🔥 Cyber Beast Optimizer v1.0.0

- One Tap Cyber Beast Mode
- Zalith Launcher Optimization
- 150+ Optimizer Strings
- Cyberpunk 3D UI"

# Create repo di GitHub & push
gh repo create Redmi14C-Cyber-Beast-Optimizer   --public   --description "Redmi 14C Cyber Beast Optimizer - Zalith Launcher Support"   --source=.   --push
```

### Step 6: Trigger Build
```bash
# Build akan otomatis trigger via GitHub Actions
# Lihat progress:
gh run list --repo $(gh repo view --json url -q .url | sed 's|https://github.com/||')

# Atau buka browser:
termux-open-url "https://github.com/$(gh api user -q .login)/Redmi14C-Cyber-Beast-Optimizer/actions"
```

### Step 7: Download APK
```bash
# Tunggu build selesai (~10-15 menit)
# Download dari GitHub Actions Artifacts

# Atau via CLI:
gh run download --repo $(gh repo view --json url -q .url | sed 's|https://github.com/||') --name CyberBeast-Optimizer-Debug
```

## 🔧 Advanced Commands

### Rebuild Manual
```bash
gh workflow run build.yml --repo username/repo-name
```

### Lihat Logs Build
```bash
gh run view --repo username/repo-name --job build
```

### Download Specific Run
```bash
# List runs
gh run list --repo username/repo-name --limit 5

# Download specific run
gh run download <RUN_ID> --repo username/repo-name --name CyberBeast-Optimizer-Release
```

### Update Project & Rebuild
```bash
cd ~/CyberBeastProjects/Redmi14C-Cyber-Beast-Optimizer
git pull origin main
# Make changes...
git add -A
git commit -m "Update: ..."
git push origin main
# Build akan auto-trigger
```

## 🐛 Troubleshooting

### Error: "gh: not found"
```bash
pkg install gh
```

### Error: "git: not found"
```bash
pkg install git
```

### Error: "Permission denied"
```bash
chmod +x gradlew
```

### Error: "JAVA_HOME not set"
```bash
# GitHub Actions handles this, tapi lokal:
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

### Build Gagal
```bash
# Cek logs
gh run view --repo username/repo-name --log

# Common fixes:
# 1. Update Gradle wrapper
./gradlew wrapper --gradle-version 8.9

# 2. Clean build
./gradlew clean

# 3. Rebuild
./gradlew assembleDebug
```

## 📦 File Structure After Extract
```
Redmi14C-Cyber-Beast-Optimizer/
├── .github/workflows/build.yml    # GitHub Actions config
├── app/
│   ├── src/main/java/...          # Source code Kotlin
│   ├── src/main/res/...           # Resources
│   └── build.gradle.kts           # App build config
├── gradle/wrapper/                # Gradle wrapper
├── build.gradle.kts               # Project build config
├── settings.gradle.kts            # Settings
├── gradlew                        # Gradle wrapper script
├── termux-build.sh                # Termux automation script
└── README.md                      # Documentation
```

## 🎮 Zalith Launcher Specific

### Optimal Settings for Redmi 14C:
```
Renderer: GL4ES (best compatibility)
JVM Memory: 4GB
JVM Args: -XX:+UseG1GC -XX:+AlwaysPreTouch -XX:+DisableExplicitGC
Resolution: 85% (for higher FPS)
VSync: OFF (uncapped FPS)
Fast Mode: ON
```

### Supported Zalith Versions:
- `com.movtery.zalithlauncher` (Release)
- `com.movtery.zalithlauncher.debug` (Debug)

## 📞 Support
- GitHub Issues: [Report Bug](https://github.com/username/Redmi14C-Cyber-Beast-Optimizer/issues)
- GitHub Discussions: [Ask Question](https://github.com/username/Redmi14C-Cyber-Beast-Optimizer/discussions)

---
**Happy Gaming! 🎮🔥**
