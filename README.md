# 🔥 REDMI 14C CYBER BEAST OPTIMIZER

<p align="center">
  <img src="https://img.shields.io/badge/Android-15%2B-brightgreen?style=for-the-badge&logo=android" />
  <img src="https://img.shields.io/badge/Kotlin-2.0-blue?style=for-the-badge&logo=kotlin" />
  <img src="https://img.shields.io/badge/Jetpack%20Compose-Latest-purple?style=for-the-badge&logo=jetpack-compose" />
  <img src="https://img.shields.io/badge/Shizuku-Integrated-cyan?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Zalith%20Launcher-Supported-green?style=for-the-badge" />
</p>

## 🎮 Tentang Aplikasi

**Redmi 14C Cyber Beast Optimizer** adalah aplikasi optimizer performa gaming premium dengan tema **Cyberpunk 3D Futuristic** yang dirancang khusus untuk **Redmi 14C (Helio G81 Ultra + HyperOS)**.

### ✨ Fitur Utama (40+ Fitur)
- 🔥 **One Tap Cyber Beast Mode**
- 🖥️ **Lock Maximum Refresh Rate** (Force 90Hz/120Hz)
- ⚡ **Force High Performance Mode**
- 🎮 **Per-App Game Profile Creator**
- 📊 **Floating FPS Monitor**
- 🔧 **150+ Optimizer Strings Database**
- 🧠 **Aggressive RAM Manager**
- 🌡️ **Thermal Throttling Reducer**
- 📡 **Network Optimizer**
- 💾 **Profile Backup & Restore**
- 🟩 **Zalith Launcher Optimization** (Minecraft Java)

## 🛠️ Persyaratan
- Android 7.0+ (API 24)
- Shizuku (Wireless Debugging / ADB WiFi)
- Target: Android 15 (API 35)

## 📥 Download
Build otomatis tersedia di [GitHub Actions](https://github.com/username/Redmi14C-Cyber-Beast-Optimizer/actions)

## 🚀 CARA BUILD VIA GITHUB ACTIONS DARI TERMUX

### Step-by-Step Lengkap

#### 1️⃣ Install Termux dari F-Droid
```bash
# Jangan install dari Play Store!
# Download dari: https://f-droid.org/packages/com.termux/
```

#### 2️⃣ Update & Install Dependencies
```bash
pkg update -y
pkg install -y git gh openssh
```

#### 3️⃣ Login ke GitHub
```bash
gh auth login
# Pilih: HTTPS → Y → Paste token (buat di github.com/settings/tokens)
```

#### 4️⃣ Download Project ZIP
```bash
# Download ZIP dari link yang diberikan
# Letakkan di /sdcard/Download/
```

#### 5️⃣ Jalankan Build Script
```bash
# Download script build
curl -o termux-build.sh https://raw.githubusercontent.com/username/Redmi14C-Cyber-Beast-Optimizer/main/termux-build.sh

# Jalankan
chmod +x termux-build.sh
./termux-build.sh
```

#### 6️⃣ Atau Manual Push
```bash
# Setup direktori
mkdir -p ~/CyberBeastProjects
cd ~/CyberBeastProjects

# Extract ZIP
unzip /sdcard/Download/Redmi14C-Cyber-Beast-Optimizer.zip

# Masuk ke project
cd Redmi14C-Cyber-Beast-Optimizer

# Init git
git init
git add -A
git commit -m "Initial commit: Cyber Beast Optimizer"

# Push ke GitHub
gh repo create Redmi14C-Cyber-Beast-Optimizer --public --source=. --push
```

#### 7️⃣ Monitor Build di GitHub
```bash
# Lihat status build
gh run list --repo username/Redmi14C-Cyber-Beast-Optimizer

# Download APK setelah selesai
# Kunjungi: https://github.com/username/Redmi14C-Cyber-Beast-Optimizer/actions
# Klik workflow terbaru → Scroll ke bawah → Download Artifacts
```

### 📱 Install APK di Redmi 14C
1. Download APK dari GitHub Actions Artifacts
2. Transfer ke HP via Bluetooth/USB
3. Install APK (izinkan "Install dari sumber tidak dikenal")
4. Jalankan aplikasi!

## 🎨 Tema Visual
- Cyberpunk 3D Futuristic Style
- Neon colors (Cyan, Magenta, Purple, Electric Blue)
- Glitch effects & holographic cards
- Particle animations & neon glow
- Dark background dengan grid lines

## 🟩 ZALITH LAUNCHER OPTIMIZATION

### Fitur Khusus Zalith:
- **6 Renderer Options**: GL4ES, ANGLE, VIRGL, ZINK, LTW, MobileGlues
- **JVM Memory**: 2GB - 8GB allocation
- **JVM Args Presets**: G1GC, AlwaysPreTouch, DisableExplicitGC, LargePages
- **Performance Tweaks**: Sustainable perf, High perf cores, Fast mode
- **One Tap Zalith Max Boost**: Apply all optimizations instantly

### Renderer Recommendations:
| Renderer | GPU | Use Case |
|----------|-----|----------|
| GL4ES | Mali/Adreno | Best compatibility (default) |
| ANGLE | Adreno | Direct3D backend |
| VIRGL | Any | Cloud/virtual GPU |
| ZINK | Adreno 6xx+ | Vulkan-based OpenGL |
| LTW | Mali | Lightweight wrapper |
| MobileGlues | Modern GPU | Best performance |

### JVM Args untuk Redmi 14C:
```
-XX:+UseG1GC
-XX:+UnlockExperimentalVMOptions
-XX:+AlwaysPreTouch
-XX:+DisableExplicitGC
-XX:MaxGCPauseMillis=30
-Xmx4G
```

## ⚠️ Disclaimer
Aplikasi ini menggunakan Shizuku untuk menjalankan perintah sistem. Penggunaan tidak benar dapat menyebabkan instabilitas sistem. Selalu backup data Anda sebelum menerapkan tweak.

## 📄 Lisensi
MIT License

---
<p align="center">Made with 🔥 for Redmi 14C Gamers</p>
