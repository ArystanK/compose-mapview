package com.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

actual typealias DisplayModifier = Modifier

@Composable
internal actual fun PlatformMapView(
    modifier: DisplayModifier,
    tiles: List<DisplayTileWithImage<TileImage>>,
    onZoom: (Pt?, Double) -> Unit,
    onClick: (Pt) -> Unit,
    onMove: (Int, Int) -> Unit,
    updateSize: (width: Int, height: Int) -> Unit,
    mapState: InternalMapState,
    markers: List<MarkerData>,
    routes: List<List<Pt>>
) {
    MapViewAndroidDesktop(
        modifier = modifier,
        tiles = tiles,
        onZoom = onZoom,
        onClick = onClick,
        onMove = onMove,
        updateSize = updateSize,
        mapState = mapState,
        markers = markers,
        routes = routes
    )
}


