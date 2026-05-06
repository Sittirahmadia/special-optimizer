package com.cyberbeast.optimizer.data.repository

import com.cyberbeast.optimizer.data.local.dao.OptimizerStringDao
import com.cyberbeast.optimizer.data.local.entity.OptimizerStringEntity
import com.cyberbeast.optimizer.data.model.OptimizerString
import com.cyberbeast.optimizer.shizuku.CommandExecutor
import com.cyberbeast.optimizer.shizuku.CommandResult
import com.cyberbeast.optimizer.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringsRepository @Inject constructor(
    private val stringDao: OptimizerStringDao,
    private val commandExecutor: CommandExecutor
) {
    fun getAllStrings(): Flow<List<OptimizerString>> =
        stringDao.getAllStrings().map { it.map { e -> e.toModel() } }

    fun getStringsByCategory(category: String): Flow<List<OptimizerString>> =
        stringDao.getStringsByCategory(category).map { it.map { e -> e.toModel() } }

    fun searchStrings(query: String): Flow<List<OptimizerString>> =
        stringDao.searchStrings(query).map { it.map { e -> e.toModel() } }

    fun getAllCategories(): Flow<List<String>> = stringDao.getAllCategories()

    suspend fun applyString(string: OptimizerString): CommandResult = withContext(Dispatchers.IO) {
        stringDao.incrementPopularity(string.id)
        commandExecutor.executeSettingsPut(string.table, string.key, string.value)
    }

    suspend fun addCustomString(string: OptimizerString): Long = withContext(Dispatchers.IO) {
        stringDao.insertString(string.toEntity())
    }

    suspend fun deleteString(string: OptimizerString) = withContext(Dispatchers.IO) {
        stringDao.deleteString(string.toEntity())
    }

    suspend fun initializeDatabase() = withContext(Dispatchers.IO) {
        if (stringDao.getCount() == 0) {
            stringDao.insertAll(getDefaultStrings())
            Timber.d("Initialized with ${getDefaultStrings().size} strings")
        }
    }

    private fun OptimizerStringEntity.toModel() = OptimizerString(
        id, name, key, value, table, description, category, fpsBoost, batteryImpact, riskLevel, isCustom, isFavorite, popularity
    )

    private fun OptimizerString.toEntity() = OptimizerStringEntity(
        id, name, key, value, table, description, category, fpsBoost, batteryImpact, riskLevel, isCustom, isFavorite, popularity
    )

    private fun getDefaultStrings(): List<OptimizerStringEntity> = listOf(
        // Refresh Rate (10)
        OptimizerStringEntity(name="Lock Peak 120Hz",key="peak_refresh_rate",value="120",table=Constants.TABLE_SYSTEM,description="Lock peak refresh rate to 120Hz",category=Constants.CAT_REFRESH_RATE,fpsBoost="+5-15",batteryImpact="High",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Lock Min 120Hz",key="min_refresh_rate",value="120",table=Constants.TABLE_SYSTEM,description="Lock minimum refresh rate to 120Hz",category=Constants.CAT_REFRESH_RATE,fpsBoost="+10-20",batteryImpact="Very High",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="User Refresh Override",key="user_refresh_rate",value="120",table=Constants.TABLE_SYSTEM,description="Override user refresh rate",category=Constants.CAT_REFRESH_RATE,fpsBoost="+5",batteryImpact="High",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Disable Auto RR",key="display_refresh_rate_mode",value="2",table=Constants.TABLE_SYSTEM,description="Disable adaptive refresh rate",category=Constants.CAT_REFRESH_RATE,fpsBoost="+3-8",batteryImpact="Medium",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="MIUI RR Lock",key="miui_refresh_rate",value="120",table=Constants.TABLE_SYSTEM,description="Lock MIUI refresh rate",category=Constants.CAT_REFRESH_RATE,fpsBoost="+5",batteryImpact="High",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="SurfaceFlinger Optim",key="debug.sf.showupdates",value="0",table=Constants.TABLE_GLOBAL,description="Optimize SurfaceFlinger",category=Constants.CAT_REFRESH_RATE,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Display Mode High",key="display_mode",value="2",table=Constants.TABLE_SYSTEM,description="Set display mode high performance",category=Constants.CAT_REFRESH_RATE,fpsBoost="+5",batteryImpact="High",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Disable Dynamic RR",key="dynamic_refresh_rate",value="0",table=Constants.TABLE_SYSTEM,description="Disable dynamic refresh",category=Constants.CAT_REFRESH_RATE,fpsBoost="+3",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Game RR Boost",key="game_refresh_rate",value="120",table=Constants.TABLE_SYSTEM,description="Boost game refresh rate",category=Constants.CAT_REFRESH_RATE,fpsBoost="+10",batteryImpact="High",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Force 90Hz",key="min_refresh_rate",value="90",table=Constants.TABLE_SYSTEM,description="Force 90Hz balance mode",category=Constants.CAT_REFRESH_RATE,fpsBoost="+5-10",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),

        // GPU Renderer (15)
        OptimizerStringEntity(name="Force GPU Render",key="debug.hwui.force_gpu_rendering",value="1",table=Constants.TABLE_GLOBAL,description="Force GPU rendering",category=Constants.CAT_GPU_RENDERER,fpsBoost="+10-25",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Vulkan Renderer",key="debug.hwui.renderer",value="vulkan",table=Constants.TABLE_GLOBAL,description="Switch to Vulkan",category=Constants.CAT_GPU_RENDERER,fpsBoost="+15-30",batteryImpact="Medium",riskLevel=Constants.RISK_HIGH),
        OptimizerStringEntity(name="SkiaGL Renderer",key="debug.hwui.renderer",value="skiagl",table=Constants.TABLE_GLOBAL,description="Use SkiaGL",category=Constants.CAT_GPU_RENDERER,fpsBoost="+5-15",batteryImpact="Low",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="SkiaVK Renderer",key="debug.hwui.renderer",value="skiavk",table=Constants.TABLE_GLOBAL,description="Skia with Vulkan",category=Constants.CAT_GPU_RENDERER,fpsBoost="+10-20",batteryImpact="Medium",riskLevel=Constants.RISK_HIGH),
        OptimizerStringEntity(name="OpenGL ES 3.2",key="debug.hwui.use_gl_3_2",value="1",table=Constants.TABLE_GLOBAL,description="Force OpenGL ES 3.2",category=Constants.CAT_GPU_RENDERER,fpsBoost="+5-10",batteryImpact="Low",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Disable HW Overlays",key="debug.hwui.disable_hw_overlays",value="1",table=Constants.TABLE_GLOBAL,description="Disable hardware overlays",category=Constants.CAT_GPU_RENDERER,fpsBoost="+3-8",batteryImpact="Medium",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="GPU Texture Cache",key="debug.hwui.texture_cache_size",value="96",table=Constants.TABLE_GLOBAL,description="Increase GPU texture cache",category=Constants.CAT_GPU_RENDERER,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Disable VSync",key="debug.hwui.disable_vsync",value="1",table=Constants.TABLE_GLOBAL,description="Disable VSync",category=Constants.CAT_GPU_RENDERER,fpsBoost="+5-10",batteryImpact="Medium",riskLevel=Constants.RISK_HIGH),
        OptimizerStringEntity(name="Show Dirty Regions Off",key="debug.hwui.show_dirty_regions",value="0",table=Constants.TABLE_GLOBAL,description="Disable debug overlay",category=Constants.CAT_GPU_RENDERER,fpsBoost="+1-3",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="GPU Profile Off",key="debug.hwui.profile",value="false",table=Constants.TABLE_GLOBAL,description="Disable GPU profiling",category=Constants.CAT_GPU_RENDERER,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Wide Color Gamut",key="debug.hwui.wide_colorgamut",value="1",table=Constants.TABLE_GLOBAL,description="Enable wide color",category=Constants.CAT_GPU_RENDERER,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="GPU Priority High",key="debug.hwui.context_priority",value="high",table=Constants.TABLE_GLOBAL,description="High GPU priority",category=Constants.CAT_GPU_RENDERER,fpsBoost="+3-5",batteryImpact="Medium",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Scissor Optim",key="debug.hwui.disable_scissor_opt",value="0",table=Constants.TABLE_GLOBAL,description="Enable scissor optimization",category=Constants.CAT_GPU_RENDERER,fpsBoost="+2-4",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Layer Buffer 8",key="debug.hwui.layer_buffer_size",value="8",table=Constants.TABLE_GLOBAL,description="Increase layer buffer",category=Constants.CAT_GPU_RENDERER,fpsBoost="+2-5",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Render Thread Priority",key="debug.hwui.render_thread_priority",value="-10",table=Constants.TABLE_GLOBAL,description="High render thread priority",category=Constants.CAT_GPU_RENDERER,fpsBoost="+3-8",batteryImpact="Medium",riskLevel=Constants.RISK_MEDIUM),

        // Animation (10)
        OptimizerStringEntity(name="Window Anim Off",key="window_animation_scale",value="0.0",table=Constants.TABLE_GLOBAL,description="Disable window animation",category=Constants.CAT_ANIMATION,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Transition Anim Off",key="transition_animation_scale",value="0.0",table=Constants.TABLE_GLOBAL,description="Disable transition animation",category=Constants.CAT_ANIMATION,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Animator Off",key="animator_duration_scale",value="0.0",table=Constants.TABLE_GLOBAL,description="Disable animator",category=Constants.CAT_ANIMATION,fpsBoost="+3-8",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Fast Window 0.25",key="window_animation_scale",value="0.25",table=Constants.TABLE_GLOBAL,description="Fast window anim",category=Constants.CAT_ANIMATION,fpsBoost="+1-3",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Fast Transition 0.25",key="transition_animation_scale",value="0.25",table=Constants.TABLE_GLOBAL,description="Fast transition",category=Constants.CAT_ANIMATION,fpsBoost="+1-3",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Fast Animator 0.25",key="animator_duration_scale",value="0.25",table=Constants.TABLE_GLOBAL,description="Fast animator",category=Constants.CAT_ANIMATION,fpsBoost="+2-4",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Scroll Optim",key="sys_vm_stats",value="0",table=Constants.TABLE_GLOBAL,description="Optimize scroll",category=Constants.CAT_ANIMATION,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Fling Boost",key="sys_vm_stats",value="0",table=Constants.TABLE_GLOBAL,description="Boost fling",category=Constants.CAT_ANIMATION,fpsBoost="+1-3",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Launch Anim Off",key="sys_vm_stats",value="0",table=Constants.TABLE_GLOBAL,description="Disable launch anim",category=Constants.CAT_ANIMATION,fpsBoost="+2-4",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Window 0.5",key="window_animation_scale",value="0.5",table=Constants.TABLE_GLOBAL,description="Balance window anim",category=Constants.CAT_ANIMATION,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),

        // Gaming (15)
        OptimizerStringEntity(name="Game Mode",key="game_mode",value="1",table=Constants.TABLE_GLOBAL,description="Enable game mode",category=Constants.CAT_GAMING,fpsBoost="+5-15",batteryImpact="High",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Game Turbo",key="game_turbo_mode",value="2",table=Constants.TABLE_GLOBAL,description="Max game turbo",category=Constants.CAT_GAMING,fpsBoost="+10-20",batteryImpact="Very High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Disable Interruptions",key="game_disable_interruptions",value="1",table=Constants.TABLE_GLOBAL,description="No interruptions",category=Constants.CAT_GAMING,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Touch Boost",key="game_touch_boost",value="1",table=Constants.TABLE_GLOBAL,description="Boost touch",category=Constants.CAT_GAMING,fpsBoost="+3-8",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Perf Priority",key="game_performance_priority",value="1",table=Constants.TABLE_GLOBAL,description="Performance priority",category=Constants.CAT_GAMING,fpsBoost="+5-10",batteryImpact="High",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Disable Auto Brightness",key="game_auto_brightness_disabled",value="1",table=Constants.TABLE_GLOBAL,description="No auto brightness",category=Constants.CAT_GAMING,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Network Priority",key="game_network_priority",value="1",table=Constants.TABLE_GLOBAL,description="Network priority",category=Constants.CAT_GAMING,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="CPU Affinity",key="game_cpu_affinity",value="1",table=Constants.TABLE_GLOBAL,description="CPU affinity",category=Constants.CAT_GAMING,fpsBoost="+5-10",batteryImpact="Medium",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="GPU Boost",key="game_gpu_boost",value="1",table=Constants.TABLE_GLOBAL,description="GPU boost",category=Constants.CAT_GAMING,fpsBoost="+10-20",batteryImpact="Very High",riskLevel=Constants.RISK_HIGH),
        OptimizerStringEntity(name="RAM Lock",key="game_ram_lock",value="1",table=Constants.TABLE_GLOBAL,description="RAM lock",category=Constants.CAT_GAMING,fpsBoost="+3-8",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Disable Assistant",key="game_assistant_disabled",value="1",table=Constants.TABLE_GLOBAL,description="No assistant",category=Constants.CAT_GAMING,fpsBoost="+2-4",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Force AA",key="game_force_aa",value="1",table=Constants.TABLE_GLOBAL,description="Force anti-aliasing",category=Constants.CAT_GAMING,fpsBoost="+0",batteryImpact="Medium",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Texture High",key="game_texture_quality",value="high",table=Constants.TABLE_GLOBAL,description="High textures",category=Constants.CAT_GAMING,fpsBoost="+0",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Frame Pacing",key="game_frame_pacing",value="1",table=Constants.TABLE_GLOBAL,description="Frame pacing",category=Constants.CAT_GAMING,fpsBoost="+3-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="HDR Mode",key="game_hdr_mode",value="1",table=Constants.TABLE_GLOBAL,description="HDR mode",category=Constants.CAT_GAMING,fpsBoost="+0",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),

        // Thermal (10)
        OptimizerStringEntity(name="Thermal Off",key="thermal_limit",value="0",table=Constants.TABLE_GLOBAL,description="Disable thermal (EXTREME RISK)",category=Constants.CAT_THERMAL,fpsBoost="+10-30",batteryImpact="Very High",riskLevel=Constants.RISK_EXTREME),
        OptimizerStringEntity(name="Thermal Engine Off",key="thermal_engine_config",value="0",table=Constants.TABLE_GLOBAL,description="Disable thermal engine",category=Constants.CAT_THERMAL,fpsBoost="+5-15",batteryImpact="High",riskLevel=Constants.RISK_EXTREME),
        OptimizerStringEntity(name="CPU Thermal Off",key="cpu_thermal_mitigation",value="0",table=Constants.TABLE_GLOBAL,description="Disable CPU thermal",category=Constants.CAT_THERMAL,fpsBoost="+5-10",batteryImpact="High",riskLevel=Constants.RISK_HIGH),
        OptimizerStringEntity(name="GPU Thermal Off",key="gpu_thermal_mitigation",value="0",table=Constants.TABLE_GLOBAL,description="Disable GPU thermal",category=Constants.CAT_THERMAL,fpsBoost="+5-15",batteryImpact="High",riskLevel=Constants.RISK_HIGH),
        OptimizerStringEntity(name="Zone0 85C",key="thermal_zone0_temp",value="85000",table=Constants.TABLE_GLOBAL,description="Zone0 85C",category=Constants.CAT_THERMAL,fpsBoost="+3-8",batteryImpact="High",riskLevel=Constants.RISK_HIGH),
        OptimizerStringEntity(name="Zone1 90C",key="thermal_zone1_temp",value="90000",table=Constants.TABLE_GLOBAL,description="Zone1 90C",category=Constants.CAT_THERMAL,fpsBoost="+3-8",batteryImpact="High",riskLevel=Constants.RISK_HIGH),
        OptimizerStringEntity(name="Thermal Sample 5s",key="thermal_sampling_rate",value="5000",table=Constants.TABLE_GLOBAL,description="Slow thermal sampling",category=Constants.CAT_THERMAL,fpsBoost="+1-3",batteryImpact="Low",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Thermal Stats Off",key="sys_vm_stats",value="0",table=Constants.TABLE_GLOBAL,description="Disable thermal stats",category=Constants.CAT_THERMAL,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Game Thermal Perf",key="game_thermal_profile",value="performance",table=Constants.TABLE_GLOBAL,description="Performance thermal",category=Constants.CAT_THERMAL,fpsBoost="+5-10",batteryImpact="High",riskLevel=Constants.RISK_HIGH),
        OptimizerStringEntity(name="Thermal VRR Off",key="thermal_vrr_disable",value="1",table=Constants.TABLE_GLOBAL,description="Disable thermal VRR",category=Constants.CAT_THERMAL,fpsBoost="+2-5",batteryImpact="Medium",riskLevel=Constants.RISK_MEDIUM),

        // RAM (10)
        OptimizerStringEntity(name="BG Process 0",key="background_process_limit",value="0",table=Constants.TABLE_GLOBAL,description="No BG limit",category=Constants.CAT_RAM,fpsBoost="+3-8",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="BG Process 4",key="background_process_limit",value="4",table=Constants.TABLE_GLOBAL,description="BG limit 4",category=Constants.CAT_RAM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Freezer Disable",key="cached_apps_freezer",value="disabled",table=Constants.TABLE_GLOBAL,description="Disable freezer",category=Constants.CAT_RAM,fpsBoost="+2-5",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Freezer Enable",key="cached_apps_freezer",value="enabled",table=Constants.TABLE_GLOBAL,description="Enable freezer",category=Constants.CAT_RAM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="App Standby Off",key="app_standby_enabled",value="0",table=Constants.TABLE_GLOBAL,description="Disable standby",category=Constants.CAT_RAM,fpsBoost="+2-5",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Forced Standby Off",key="forced_app_standby_enabled",value="0",table=Constants.TABLE_GLOBAL,description="Disable forced standby",category=Constants.CAT_RAM,fpsBoost="+1-3",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Auto Restriction Off",key="app_auto_restriction_enabled",value="0",table=Constants.TABLE_GLOBAL,description="Disable auto restriction",category=Constants.CAT_RAM,fpsBoost="+1-3",batteryImpact="Medium",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Mem Stats Off",key="sys_vm_stats",value="0",table=Constants.TABLE_GLOBAL,description="Disable mem stats",category=Constants.CAT_RAM,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="LMK Report Off",key="sys_lmk_report",value="0",table=Constants.TABLE_GLOBAL,description="Disable LMK report",category=Constants.CAT_RAM,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="RAM Boost",key="ram_boost_mode",value="1",table=Constants.TABLE_GLOBAL,description="RAM boost",category=Constants.CAT_RAM,fpsBoost="+3-8",batteryImpact="Medium",riskLevel=Constants.RISK_MEDIUM),

        // Network (10)
        OptimizerStringEntity(name="DNS Cloudflare",key="net_dns1",value="1.1.1.1",table=Constants.TABLE_GLOBAL,description="Cloudflare DNS",category=Constants.CAT_NETWORK,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="DNS CF Secondary",key="net_dns2",value="1.0.0.1",table=Constants.TABLE_GLOBAL,description="Cloudflare DNS 2",category=Constants.CAT_NETWORK,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="DNS Google",key="net_dns3",value="8.8.8.8",table=Constants.TABLE_GLOBAL,description="Google DNS",category=Constants.CAT_NETWORK,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="TCP WiFi Optim",key="net_tcp_buffersize_wifi",value="524288,1048576,2097152,262144,524288,1048576",table=Constants.TABLE_GLOBAL,description="TCP WiFi optimized",category=Constants.CAT_NETWORK,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="TCP LTE Optim",key="net_tcp_buffersize_lte",value="524288,1048576,2097152,262144,524288,1048576",table=Constants.TABLE_GLOBAL,description="TCP LTE optimized",category=Constants.CAT_NETWORK,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="TCP RWND 60",key="tcp_default_init_rwnd",value="60",table=Constants.TABLE_GLOBAL,description="TCP rwnd 60",category=Constants.CAT_NETWORK,fpsBoost="+1-3",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Tether DUN Off",key="tether_dun_required",value="0",table=Constants.TABLE_GLOBAL,description="Disable tether DUN",category=Constants.CAT_NETWORK,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="WiFi Scan 300s",key="wifi_scan_interval",value="300",table=Constants.TABLE_GLOBAL,description="WiFi scan 300s",category=Constants.CAT_NETWORK,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Mobile Always On",key="mobile_data_always_on",value="1",table=Constants.TABLE_GLOBAL,description="Mobile always on",category=Constants.CAT_NETWORK,fpsBoost="+0",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Avoid Bad WiFi Off",key="network_avoid_bad_wifi",value="0",table=Constants.TABLE_GLOBAL,description="Stay on WiFi",category=Constants.CAT_NETWORK,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),

        // Touch (10)
        OptimizerStringEntity(name="Pointer Speed Max",key="pointer_speed",value="7",table=Constants.TABLE_SYSTEM,description="Max pointer speed",category=Constants.CAT_TOUCH,fpsBoost="+3-8",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Pointer Default",key="pointer_speed",value="0",table=Constants.TABLE_SYSTEM,description="Default pointer",category=Constants.CAT_TOUCH,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Long Press Fast",key="long_press_timeout",value="200",table=Constants.TABLE_SYSTEM,description="Fast long press",category=Constants.CAT_TOUCH,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Long Press Default",key="long_press_timeout",value="400",table=Constants.TABLE_SYSTEM,description="Default long press",category=Constants.CAT_TOUCH,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Multi Press Fast",key="multi_press_timeout",value="150",table=Constants.TABLE_SYSTEM,description="Fast multi press",category=Constants.CAT_TOUCH,fpsBoost="+1-3",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Tap Duration 0",key="tap_duration",value="0",table=Constants.TABLE_SYSTEM,description="Instant tap",category=Constants.CAT_TOUCH,fpsBoost="+2-4",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Touch Sensitivity Max",key="touch_sensitivity",value="10",table=Constants.TABLE_SYSTEM,description="Max sensitivity",category=Constants.CAT_TOUCH,fpsBoost="+3-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Touch Debounce Off",key="touch_debounce",value="0",table=Constants.TABLE_SYSTEM,description="No debounce",category=Constants.CAT_TOUCH,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Show Touches Off",key="show_touches",value="0",table=Constants.TABLE_SYSTEM,description="No touch visual",category=Constants.CAT_TOUCH,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Pointer Location Off",key="pointer_location",value="0",table=Constants.TABLE_SYSTEM,description="No pointer overlay",category=Constants.CAT_TOUCH,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),

        // Storage (8)
        OptimizerStringEntity(name="Read Ahead 1024",key="read_ahead_kb",value="1024",table=Constants.TABLE_GLOBAL,description="Read ahead 1024KB",category=Constants.CAT_STORAGE,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Read Ahead 512",key="read_ahead_kb",value="512",table=Constants.TABLE_GLOBAL,description="Read ahead 512KB",category=Constants.CAT_STORAGE,fpsBoost="+1-3",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="IO Stats Off",key="io_stats",value="0",table=Constants.TABLE_GLOBAL,description="Disable IO stats",category=Constants.CAT_STORAGE,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Storage TRIM",key="storage_trim_enabled",value="1",table=Constants.TABLE_GLOBAL,description="Enable TRIM",category=Constants.CAT_STORAGE,fpsBoost="+1-3",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="FSTRIM 24h",key="fstrim_interval",value="86400",table=Constants.TABLE_GLOBAL,description="FSTRIM 24h",category=Constants.CAT_STORAGE,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Lazy Preload Off",key="lazy_preload",value="0",table=Constants.TABLE_GLOBAL,description="Disable lazy preload",category=Constants.CAT_STORAGE,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Dalvik Clean",key="dalvik_cache_clean",value="1",table=Constants.TABLE_GLOBAL,description="Clean dalvik",category=Constants.CAT_STORAGE,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="ART Optimized",key="art_cache_config",value="optimized",table=Constants.TABLE_GLOBAL,description="ART optimized",category=Constants.CAT_STORAGE,fpsBoost="+2-4",batteryImpact="Low",riskLevel=Constants.RISK_LOW),

        // Sensor (8)
        OptimizerStringEntity(name="Auto Rotate Off",key="accelerometer_rotation",value="0",table=Constants.TABLE_SYSTEM,description="No auto rotate",category=Constants.CAT_SENSOR,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Proximity Off",key="proximity_sensor",value="0",table=Constants.TABLE_SYSTEM,description="No proximity",category=Constants.CAT_SENSOR,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Light Sensor Off",key="light_sensor",value="0",table=Constants.TABLE_SYSTEM,description="No light sensor",category=Constants.CAT_SENSOR,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Magnetic Off",key="magnetic_sensor",value="0",table=Constants.TABLE_SYSTEM,description="No magnetic",category=Constants.CAT_SENSOR,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Gyroscope Off",key="gyroscope_sensor",value="0",table=Constants.TABLE_SYSTEM,description="No gyroscope",category=Constants.CAT_SENSOR,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Step Counter Off",key="step_counter",value="0",table=Constants.TABLE_SYSTEM,description="No step counter",category=Constants.CAT_SENSOR,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Motion Detect Off",key="significant_motion",value="0",table=Constants.TABLE_SYSTEM,description="No motion detect",category=Constants.CAT_SENSOR,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Sensor Rate Low",key="sensor_sampling_rate",value="0",table=Constants.TABLE_SYSTEM,description="Low sensor rate",category=Constants.CAT_SENSOR,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),

        // Battery (10)
        OptimizerStringEntity(name="Adaptive Battery Off",key="adaptive_battery_management_enabled",value="0",table=Constants.TABLE_GLOBAL,description="Disable adaptive battery",category=Constants.CAT_BATTERY,fpsBoost="+2-5",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Saver Restrictions Off",key="enable_battery_saver_user_restrictions",value="0",table=Constants.TABLE_GLOBAL,description="Disable saver restrictions",category=Constants.CAT_BATTERY,fpsBoost="+1-3",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Doze Off",key="doze_mode_enabled",value="0",table=Constants.TABLE_GLOBAL,description="Disable doze",category=Constants.CAT_BATTERY,fpsBoost="+1-2",batteryImpact="Very High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Light Doze Off",key="light_doze_enabled",value="0",table=Constants.TABLE_GLOBAL,description="Disable light doze",category=Constants.CAT_BATTERY,fpsBoost="+1-2",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Deep Doze Off",key="deep_doze_enabled",value="0",table=Constants.TABLE_GLOBAL,description="Disable deep doze",category=Constants.CAT_BATTERY,fpsBoost="+1-2",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Battery Stats Off",key="battery_stats_enable",value="0",table=Constants.TABLE_GLOBAL,description="Disable battery stats",category=Constants.CAT_BATTERY,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Power Monitor Off",key="power_monitor",value="0",table=Constants.TABLE_GLOBAL,description="Disable power monitor",category=Constants.CAT_BATTERY,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Low Trigger 10%",key="low_power_trigger_level",value="10",table=Constants.TABLE_GLOBAL,description="Low trigger 10%",category=Constants.CAT_BATTERY,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Charging Anim Off",key="charging_animation",value="0",table=Constants.TABLE_SYSTEM,description="No charging anim",category=Constants.CAT_BATTERY,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Fast Charge",key="fast_charge_mode",value="1",table=Constants.TABLE_SYSTEM,description="Fast charge",category=Constants.CAT_BATTERY,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),

        // UI Smooth (8)
        OptimizerStringEntity(name="Blur Off",key="disable_blur",value="1",table=Constants.TABLE_SYSTEM,description="Disable blur",category=Constants.CAT_UI_SMOOTH,fpsBoost="+3-8",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Transparency Off",key="disable_transparency",value="1",table=Constants.TABLE_SYSTEM,description="Disable transparency",category=Constants.CAT_UI_SMOOTH,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Window Effects Off",key="window_animation_scale",value="0.0",table=Constants.TABLE_GLOBAL,description="No window effects",category=Constants.CAT_UI_SMOOTH,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Surface Updates Off",key="show_surface_updates",value="0",table=Constants.TABLE_SYSTEM,description="No surface updates",category=Constants.CAT_UI_SMOOTH,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Strict Mode Off",key="strict_mode",value="0",table=Constants.TABLE_GLOBAL,description="No strict mode",category=Constants.CAT_UI_SMOOTH,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="GPU Profile Off",key="debug.hwui.profile",value="false",table=Constants.TABLE_GLOBAL,description="No GPU profile",category=Constants.CAT_UI_SMOOTH,fpsBoost="+2-4",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="CPU Usage Off",key="show_cpu_usage",value="0",table=Constants.TABLE_GLOBAL,description="No CPU overlay",category=Constants.CAT_UI_SMOOTH,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="BG Processes Off",key="show_background_processes",value="0",table=Constants.TABLE_GLOBAL,description="No BG indicator",category=Constants.CAT_UI_SMOOTH,fpsBoost="+1",batteryImpact="Low",riskLevel=Constants.RISK_LOW),

        // System (10)
        OptimizerStringEntity(name="Device Provisioned",key="device_provisioned",value="1",table=Constants.TABLE_GLOBAL,description="Device provisioned",category=Constants.CAT_SYSTEM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="User Setup Done",key="user_setup_complete",value="1",table=Constants.TABLE_SECURE,description="Setup complete",category=Constants.CAT_SYSTEM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Dev Settings On",key="development_settings_enabled",value="1",table=Constants.TABLE_GLOBAL,description="Dev settings on",category=Constants.CAT_SYSTEM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="ADB On",key="adb_enabled",value="1",table=Constants.TABLE_GLOBAL,description="ADB enabled",category=Constants.CAT_SYSTEM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Stay On Plugged",key="stay_on_while_plugged_in",value="3",table=Constants.TABLE_GLOBAL,description="Stay on plugged",category=Constants.CAT_SYSTEM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Screen Timeout 30m",key="screen_off_timeout",value="1800000",table=Constants.TABLE_SYSTEM,description="30min timeout",category=Constants.CAT_SYSTEM,fpsBoost="+0",batteryImpact="High",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="DTMF Off",key="dtmf_tone",value="0",table=Constants.TABLE_SYSTEM,description="No DTMF",category=Constants.CAT_SYSTEM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Sound Effects Off",key="sound_effects_enabled",value="0",table=Constants.TABLE_SYSTEM,description="No sound effects",category=Constants.CAT_SYSTEM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Haptic Off",key="haptic_feedback_enabled",value="0",table=Constants.TABLE_SYSTEM,description="No haptic",category=Constants.CAT_SYSTEM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Lockscreen Sounds Off",key="lockscreen_sounds_enabled",value="0",table=Constants.TABLE_SYSTEM,description="No lock sounds",category=Constants.CAT_SYSTEM,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),

        // Ads (8)
        OptimizerStringEntity(name="MSA Global Off",key="msa_global",value="0",table=Constants.TABLE_GLOBAL,description="Disable MSA",category=Constants.CAT_ADS,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Analytics Off",key="analytics_enabled",value="0",table=Constants.TABLE_GLOBAL,description="Disable analytics",category=Constants.CAT_ADS,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Recommendations Off",key="recommendations_enabled",value="0",table=Constants.TABLE_GLOBAL,description="No recommendations",category=Constants.CAT_ADS,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="App Suggestions Off",key="app_suggestions_enabled",value="0",table=Constants.TABLE_GLOBAL,description="No suggestions",category=Constants.CAT_ADS,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Personalized Ads Off",key="personalized_ads_enabled",value="0",table=Constants.TABLE_GLOBAL,description="No personalized ads",category=Constants.CAT_ADS,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Joyose Off",key="joyose_enabled",value="0",table=Constants.TABLE_GLOBAL,description="Disable Joyose",category=Constants.CAT_ADS,fpsBoost="+1-3",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Hybrid Off",key="hybrid_enabled",value="0",table=Constants.TABLE_GLOBAL,description="Disable hybrid",category=Constants.CAT_ADS,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="System Ad Off",key="system_ad_solution",value="0",table=Constants.TABLE_GLOBAL,description="No system ads",category=Constants.CAT_ADS,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),

        // Debug (7)
        
        // Zalith Launcher (10)
        OptimizerStringEntity(name="Zalith GL4ES",key="zalith.renderer",value="gl4es",table=Constants.TABLE_GLOBAL,description="Zalith GL4ES renderer (Mali/Adreno best)",category=Constants.CAT_GAMING,fpsBoost="+20-40",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Zalith ANGLE",key="zalith.renderer",value="angle",table=Constants.TABLE_GLOBAL,description="Zalith ANGLE renderer (Direct3D)",category=Constants.CAT_GAMING,fpsBoost="+15-30",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Zalith ZINK",key="zalith.renderer",value="zink",table=Constants.TABLE_GLOBAL,description="Zalith ZINK Vulkan renderer",category=Constants.CAT_GAMING,fpsBoost="+25-50",batteryImpact="High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Zalith JVM 4G",key="zalith.jvm.maxmemory",value="4G",table=Constants.TABLE_GLOBAL,description="Zalith 4GB RAM allocation",category=Constants.CAT_GAMING,fpsBoost="+10-20",batteryImpact="High",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Zalith JVM 6G",key="zalith.jvm.maxmemory",value="6G",table=Constants.TABLE_GLOBAL,description="Zalith 6GB RAM allocation",category=Constants.CAT_GAMING,fpsBoost="+15-25",batteryImpact="Very High",riskLevel=Constants.RISK_MEDIUM),
        OptimizerStringEntity(name="Zalith G1GC",key="zalith.jvm.g1gc",value="-XX:+UseG1GC",table=Constants.TABLE_GLOBAL,description="Zalith G1 Garbage Collector",category=Constants.CAT_GAMING,fpsBoost="+5-15",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Zalith PreTouch",key="zalith.jvm.pretouch",value="-XX:+AlwaysPreTouch",table=Constants.TABLE_GLOBAL,description="Zalith AlwaysPreTouch memory",category=Constants.CAT_GAMING,fpsBoost="+3-8",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Zalith Fast Mode",key="zalith_fast_mode",value="1",table=Constants.TABLE_SYSTEM,description="Zalith fast graphics mode",category=Constants.CAT_GAMING,fpsBoost="+10-20",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Zalith VSync Off",key="zalith_vsync",value="0",table=Constants.TABLE_SYSTEM,description="Zalith disable VSync (uncapped FPS)",category=Constants.CAT_GAMING,fpsBoost="+5-10",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Zalith Sustainable",key="zalith_sustainable_perf",value="1",table=Constants.TABLE_SYSTEM,description="Zalith sustainable performance mode",category=Constants.CAT_GAMING,fpsBoost="+5-15",batteryImpact="Medium",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Debug Layout Off",key="debug_layout",value="0",table=Constants.TABLE_GLOBAL,description="No debug layout",category=Constants.CAT_DEBUG,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="GPU Overdraw Off",key="debug_hwui_overdraw",value="0",table=Constants.TABLE_GLOBAL,description="No overdraw",category=Constants.CAT_DEBUG,fpsBoost="+2-4",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Profile Bars Off",key="debug_hwui_profile_bars",value="0",table=Constants.TABLE_GLOBAL,description="No profile bars",category=Constants.CAT_DEBUG,fpsBoost="+2-4",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Show FPS Off",key="debug_show_fps",value="0",table=Constants.TABLE_GLOBAL,description="No FPS overlay",category=Constants.CAT_DEBUG,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="Force RTL Off",key="debug_force_rtl",value="0",table=Constants.TABLE_GLOBAL,description="No RTL force",category=Constants.CAT_DEBUG,fpsBoost="+0",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="HWUI Debug Off",key="debug_hwui",value="0",table=Constants.TABLE_GLOBAL,description="No HWUI debug",category=Constants.CAT_DEBUG,fpsBoost="+2-5",batteryImpact="Low",riskLevel=Constants.RISK_LOW),
        OptimizerStringEntity(name="View Attr Off",key="debug_view_attributes",value="0",table=Constants.TABLE_GLOBAL,description="No view attr",category=Constants.CAT_DEBUG,fpsBoost="+1-2",batteryImpact="Low",riskLevel=Constants.RISK_LOW)
    )
}
