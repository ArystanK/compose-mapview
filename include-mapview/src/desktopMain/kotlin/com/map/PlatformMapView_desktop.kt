package com.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import com.map.ui.MapViewAndroidDesktop
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import java.awt.Rectangle
import java.io.File

@Composable
internal actual fun createImageRepositoryComposable(ioScope: CoroutineScope): ContentRepository<Tile, GpuOptimizedImage> {
    // Для HOME директории MacOS требует разрешения.
    // Чтобы не просить разрешений созданим кэш во временной директории.
    val cacheDir = File(System.getProperty("java.io.tmpdir")).resolve(CACHE_DIR_NAME)
    return createRealRepository(HttpClient(CIO))
        .decorateWithLimitRequestsInParallel(ioScope)
        .decorateWithDiskCache(ioScope, cacheDir)
        .adapter { GpuOptimizedImage(it.toImageBitmap()) }
        .decorateWithDistinctDownloader(ioScope)
        .decorateWithInMemoryCache()
}

actual typealias DisplayModifier = Modifier

@Composable
internal actual fun PlatformMapView(
    modifier: DisplayModifier,
    stateFlow: StateFlow<ImageTilesGrid>,
    onZoom: (Pt?, Double) -> Unit,
    onClick: (Pt) -> Unit,
    onMove: (Int, Int) -> Unit,
    updateSize: (width: Int, height: Int) -> Unit
) {
    MapViewAndroidDesktop(
        modifier = modifier,
        isInTouchMode = false,
        stateFlow = stateFlow,
        onZoom = onZoom,
        onClick = onClick,
        onMove = onMove,
        updateSize = updateSize
    )
}

@Composable
internal actual fun Telemetry(stateFlow: StateFlow<MapState>) {
    val state by stateFlow.collectAsState()
    Column(Modifier.background(Color(0x77ffFFff))) {
        Text(state.toShortString())
    }
}

private fun GpuOptimizedImage.cropAndScale(x: Int, y: Int, w: Int, h: Int, targetW: Int, targetH: Int): GpuOptimizedImage {
    val cropped = cropImage(extract().toAwtImage(), Rectangle(x, y, w, h))
    val scaled = scaleBitmapAspectRatio(cropped, targetW, targetH)
    val result = GpuOptimizedImage(scaled.toComposeImageBitmap())
    return result
    if(false) {
        return crop(x,y,w,h).scale(targetW, targetH)
    }
    if (false) {
        extract().asSkiaBitmap().asComposeImageBitmap()
    }
}

private fun GpuOptimizedImage.crop(x: Int, y: Int, w: Int, h: Int): GpuOptimizedImage {
    return GpuOptimizedImage(cropImage(extract().toAwtImage(), Rectangle(x, y, w, h)).toComposeImageBitmap())
}

private fun GpuOptimizedImage.scale(w: Int, h: Int): GpuOptimizedImage {
    val result = scaleBitmapAspectRatio(extract().toAwtImage(), w, h).toComposeImageBitmap()
    if (result.width != w || result.height != h) {
        throw Exception("my exception result.width != w || result.height != h")
    }
    return GpuOptimizedImage(result)
}