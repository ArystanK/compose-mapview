package com.map

val TILE_SIZE = 512

object Config {
    val DISPLAY_TELEMETRY: Boolean = true
    val SIMULATE_NETWORK_PROBLEMS = false
    val CLICK_DURATION_MS: Long = 300
    val CLICK_AREA_RADIUS_PX: Int = 7
    val ZOOM_ON_CLICK = 0.8
    val MAX_SCALE_ON_SINGLE_ZOOM_EVENT = 2.0
    val SCROLL_SENSITIVITY_DESKTOP = 0.05
    val SCROLL_SENSITIVITY_BROWSER = 0.001
    val CACHE_DIR_NAME = "map-view-cache"
    val MIN_ZOOM = 0
    val MAX_ZOOM = 22

    fun createTileUrl(zoom: Int, x: Int, y: Int, mapTilerSecretKey: String): String =
        "https://api.maptiler.com/maps/streets/$zoom/$x/$y.png?key=$mapTilerSecretKey"
}