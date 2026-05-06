package com.cyberbeast.optimizer.utils

object Constants {
    const val APP_NAME = "Redmi 14C Cyber Beast Optimizer"
    const val VERSION = "1.0.0-CyberBeast"

    // Shizuku
    const val SHIZUKU_PACKAGE = "moe.shizuku.privileged.api"
    const val SHIZUKU_REQUEST_CODE = 8710

    // Database
    const val DATABASE_NAME = "cyberbeast_optimizer.db"
    const val DATABASE_VERSION = 1

    // Preferences
    const val PREFS_NAME = "cyberbeast_prefs"
    const val PREF_ACTIVE_PROFILE = "active_profile"
    const val PREF_SHIZUKU_ENABLED = "shizuku_enabled"
    const val PREF_FIRST_RUN = "first_run"
    const val PREF_LANGUAGE = "app_language"

    // Tables for Settings Provider
    const val TABLE_GLOBAL = "global"
    const val TABLE_SYSTEM = "system"
    const val TABLE_SECURE = "secure"

    // Categories
    const val CAT_REFRESH_RATE = "refresh_rate"
    const val CAT_GPU_RENDERER = "gpu_renderer"
    const val CAT_ANIMATION = "animation"
    const val CAT_GAMING = "gaming"
    const val CAT_THERMAL = "thermal"
    const val CAT_RAM = "ram"
    const val CAT_NETWORK = "network"
    const val CAT_TOUCH = "touch"
    const val CAT_STORAGE = "storage"
    const val CAT_SENSOR = "sensor"
    const val CAT_BATTERY = "battery"
    const val CAT_UI_SMOOTH = "ui_smooth"
    const val CAT_SYSTEM = "system"
    const val CAT_ADS = "ads"
    const val CAT_DEBUG = "debug"

    // Risk Levels
    const val RISK_LOW = "low"
    const val RISK_MEDIUM = "medium"
    const val RISK_HIGH = "high"
    const val RISK_EXTREME = "extreme"

    // Profiles
    const val PROFILE_CYBER_BEAST = "cyber_beast"
    const val PROFILE_EXTREME_FPS = "extreme_fps"
    const val PROFILE_BALANCED = "balanced"
    const val PROFILE_BATTERY = "battery_saver"
    const val PROFILE_CUSTOM = "custom"

    // Game Package Names
    val GAME_PACKAGES = listOf(
        "com.tencent.ig",           // PUBG Mobile
        "com.pubg.krmobile",        // PUBG KR
        "com.vng.pubgmobile",       // PUBG VN
        "com.rekoo.pubgm",          // PUBG TW
        "com.mobile.legends",       // Mobile Legends
        "com.miHoYo.GenshinImpact", // Genshin Impact
        "com.activision.callofduty.shooter", // CODM
        "com.dts.freefireth",       // Free Fire
        "com.dts.freefiremax",      // Free Fire Max
        "com.garena.game.codm",     // CODM Garena
        "com.garena.game.kgtw",     // Arena of Valor
        "com.netease.lztg",         // Naraka
        "com.epicgames.fortnite",   // Fortnite
        "com.ea.gp.fifamobile",     // FIFA Mobile
        "com.supercell.clashroyale", // Clash Royale
        "com.supercell.brawlstars", // Brawl Stars
        "com.kurogame.wutheringwaves", // Wuthering Waves
        "com.hoYoverse.h3rdjp",     // Honkai Impact
        "com.hoYoverse.starRail",   // Honkai Star Rail
        "com.levelinfinite.sgameGlobal", // Honor of Kings
        "com.movtery.zalithlauncher", // Zalith Launcher
        "com.movtery.zalithlauncher.debug" // Zalith Launcher Debug
    )

    // Commands
    const val CMD_SETTINGS_PUT = "settings put"
    const val CMD_SETTINGS_GET = "settings get"
    const val CMD_SETPROP = "setprop"
    const val CMD_GETPROP = "getprop"
    const val CMD_PM = "pm"
    const val CMD_AM = "am"
    const val CMD_DUMPSYS = "dumpsys"

    // Animation Scales
    const val ANIM_SCALE_OFF = "0.0"
    const val ANIM_SCALE_LOW = "0.25"
    const val ANIM_SCALE_DEFAULT = "0.5"
    const val ANIM_SCALE_NORMAL = "1.0"

    // Refresh Rate Values
    const val REFRESH_60 = "60"
    const val REFRESH_90 = "90"
    const val REFRESH_120 = "120"

    // Governor Values
    const val GOV_PERFORMANCE = "performance"
    const val GOV_SCHEDUTIL = "schedutil"
    const val GOV_POWERSAVE = "powersave"
    const val GOV_INTERACTIVE = "interactive"


    // Zalith Launcher Settings
    const val ZALITH_PACKAGE = "com.movtery.zalithlauncher"
    const val ZALITH_DEBUG_PACKAGE = "com.movtery.zalithlauncher.debug"
    const val ZALITH_PREF_FILE = "zalith_launcher_prefs"

    // Zalith Renderer Options
    const val ZALITH_RENDERER_GL4ES = "gl4es"
    const val ZALITH_RENDERER_ANGLE = "angle"
    const val ZALITH_RENDERER_VIRGL = "virgl"
    const val ZALITH_RENDERER_ZINK = "zink"
    const val ZALITH_RENDERER_LTW = "ltw"

    // Zalith JVM Args
    const val ZALITH_JVM_DEFAULT = "-Xmx2G"
    const val ZALITH_JVM_PERFORMANCE = "-Xmx4G -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions"
    const val ZALITH_JVM_AGGRESSIVE = "-Xmx6G -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:+AlwaysPreTouch"

    // I/O Schedulers
    const val IO_NOOP = "noop"
    const val IO_DEADLINE = "deadline"
    const val IO_CFQ = "cfq"
    const val IO_BFQ = "bfq"
    const val IO_ZEN = "zen"
    const val IO_FIOPS = "fiops"

    // Renderer Values
    const val RENDERER_OPENGL = "opengl"
    const val RENDERER_VULKAN = "vulkan"
    const val RENDERER_SKIA = "skiagl"
    const val RENDERER_SKIA_VK = "skiavk"
}
