#!/data/data/com.termux/files/usr/bin/bash
# Cyber Beast Optimizer - Termux Build Script for GitHub Actions
# Usage: ./termux-build.sh

set -e

RED='[0;31m'
GREEN='[0;32m'
CYAN='[0;36m'
YELLOW='[1;33m'
NC='[0m' # No Color

echo -e "${CYAN}"
echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║     🔥 REDMI 14C CYBER BEAST OPTIMIZER - TERMUX BUILD        ║"
echo "║              GitHub Actions Deployment Script                   ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo -e "${NC}"

# Check if running in Termux
if [ ! -d "/data/data/com.termux/files" ]; then
    echo -e "${RED}Error: This script must run in Termux!${NC}"
    exit 1
fi

# Check required packages
echo -e "${YELLOW}[1/8] Checking dependencies...${NC}"
REQUIRED_PKGS="git gh openssh"
MISSING_PKGS=""

for pkg in $REQUIRED_PKGS; do
    if ! command -v $pkg &> /dev/null; then
        MISSING_PKGS="$MISSING_PKGS $pkg"
    fi
done

if [ ! -z "$MISSING_PKGS" ]; then
    echo -e "${YELLOW}Installing missing packages:$MISSING_PKGS${NC}"
    pkg update -y
    pkg install -y $MISSING_PKGS
fi

# Check GitHub CLI auth
echo -e "${YELLOW}[2/8] Checking GitHub authentication...${NC}"
if ! gh auth status &> /dev/null; then
    echo -e "${CYAN}Please login to GitHub:${NC}"
    gh auth login
fi

GH_USER=$(gh api user -q .login)
echo -e "${GREEN}✓ Logged in as: $GH_USER${NC}"

# Get repository info
echo -e "${YELLOW}[3/8] Repository setup...${NC}"
read -p "Enter repository name [Redmi14C-Cyber-Beast-Optimizer]: " REPO_NAME
REPO_NAME=${REPO_NAME:-Redmi14C-Cyber-Beast-Optimizer}

read -p "Make repo private? [y/N]: " PRIVATE
PRIVATE_FLAG=""
if [[ $PRIVATE =~ ^[Yy]$ ]]; then
    PRIVATE_FLAG="--private"
fi

# Check if repo exists
if gh repo view "$GH_USER/$REPO_NAME" &> /dev/null; then
    echo -e "${GREEN}✓ Repository exists: $GH_USER/$REPO_NAME${NC}"
    REPO_EXISTS=true
else
    echo -e "${YELLOW}Creating repository...${NC}"
    gh repo create "$REPO_NAME" $PRIVATE_FLAG --description "Redmi 14C Cyber Beast Optimizer - Zalith Launcher Support" --source=. --push
    REPO_EXISTS=false
fi

# Clone or navigate to repo
PROJECT_DIR="$HOME/CyberBeastProjects"
mkdir -p "$PROJECT_DIR"
cd "$PROJECT_DIR"

if [ "$REPO_EXISTS" = true ]; then
    if [ -d "$REPO_NAME" ]; then
        echo -e "${YELLOW}[4/8] Updating existing project...${NC}"
        cd "$REPO_NAME"
        git pull origin main
    else
        echo -e "${YELLOW}[4/8] Cloning repository...${NC}"
        gh repo clone "$GH_USER/$REPO_NAME"
        cd "$REPO_NAME"
    fi
else
    echo -e "${YELLOW}[4/8] Project created locally...${NC}"
    cd "$REPO_NAME"
fi

# Extract project files (if ZIP provided)
if [ -f "$HOME/Downloads/Redmi14C-Cyber-Beast-Optimizer.zip" ]; then
    echo -e "${YELLOW}[5/8] Extracting project files...${NC}"
    unzip -o "$HOME/Downloads/Redmi14C-Cyber-Beast-Optimizer.zip" -d /tmp/cyberbeast/
    cp -r /tmp/cyberbeast/Redmi14C-Cyber-Beast-Optimizer/* .
    rm -rf /tmp/cyberbeast/
fi

# Configure git
echo -e "${YELLOW}[6/8] Configuring git...${NC}"
git config user.name "Termux Builder"
git config user.email "termux@cyberbeast.local"

# Add all files
echo -e "${YELLOW}[7/8] Adding files to git...${NC}"
git add -A
git commit -m "🔥 Cyber Beast Optimizer v1.0.0 - Zalith Launcher Support

Features:
- One Tap Cyber Beast Mode
- Zalith Launcher Optimization (Minecraft Java)
- 150+ Optimizer Strings Database
- 6 Renderer Pipeline Options (GL4ES, ANGLE, VIRGL, ZINK, LTW, MobileGlues)
- JVM Memory Allocation (2GB-8GB)
- G1GC & Performance JVM Args
- Real-time System Monitor
- Profile Manager (Cyber Beast, Extreme FPS, Balanced, Battery)
- Game Profiles (PUBG, MLBB, CODM, Genshin, Free Fire, Zalith)
- Shizuku Integration
- Cyberpunk 3D UI

Built via GitHub Actions from Termux
Device: Redmi 14C (Helio G81 Ultra + HyperOS)"

# Push to GitHub
echo -e "${YELLOW}[8/8] Pushing to GitHub...${NC}"
git push -u origin main

echo -e "${GREEN}"
echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║                    ✅ PUSH SUCCESSFUL!                        ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo -e "${NC}"

echo -e "${CYAN}Repository: ${GREEN}https://github.com/$GH_USER/$REPO_NAME${NC}"
echo -e "${CYAN}GitHub Actions: ${GREEN}https://github.com/$GH_USER/$REPO_NAME/actions${NC}"
echo ""
echo -e "${YELLOW}Next steps:${NC}"
echo -e "1. Visit the Actions tab on GitHub"
echo -e "2. Wait for build to complete (~10-15 minutes)"
echo -e "3. Download APK from Artifacts section"
echo -e "4. Install on your Redmi 14C"
echo ""
echo -e "${CYAN}To trigger rebuild:${NC}"
echo -e "  gh workflow run build.yml --repo $GH_USER/$REPO_NAME"
echo ""
echo -e "${CYAN}To download latest APK:${NC}"
echo -e "  gh run list --repo $GH_USER/$REPO_NAME --workflow=build.yml"
