@file:OptIn(ExperimentalComposeUiApi::class)

package com.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun MapViewAndroidDesktop(
    modifier: Modifier,
    isInTouchMode: Boolean = false,
    tiles: List<DisplayTileWithImage<TileImage>>,
    onZoom: (Pt?, Double) -> Unit,
    onClick: (Pt) -> Unit,
    onMove: (Int, Int) -> Unit,
    updateSize: (width: Int, height: Int) -> Unit,
    mapState: InternalMapState,
    markers: List<MarkerData>,
    routes: List<List<Pt>>
) {
    var previousMoveDownPos by remember { mutableStateOf<Offset?>(null) }
    var previousPressTime by remember { mutableStateOf(0L) }
    var previousPressPos by remember { mutableStateOf<Offset?>(null) }

    fun Modifier.applyPointerInput() = pointerInput(Unit) {
        while (true) {
            val event = awaitPointerEventScope {
                awaitPointerEvent()
            }
            val current = event.changes.firstOrNull()?.position
            if (event.type == PointerEventType.Scroll) {
                val scrollY: Float? = event.changes.firstOrNull()?.scrollDelta?.y
                if (scrollY != null && scrollY != 0f) {
                    onZoom(current?.toPt(), -scrollY * Config.SCROLL_SENSITIVITY_DESKTOP)
                }
            }
            when (event.type) {
                PointerEventType.Move -> {
                    if (event.buttons.isPrimaryPressed || isInTouchMode) {
                        val previous = previousMoveDownPos
                        if (previous != null && current != null) {
                            val dx = (current.x - previous.x).toInt()
                            val dy = (current.y - previous.y).toInt()
                            if (dx != 0 || dy != 0) {
                                onMove(dx, dy)
                            }
                        }
                        previousMoveDownPos = current
                    } else {
                        previousMoveDownPos = null
                    }
                }
                PointerEventType.Press -> {
                    previousPressTime = timeMs()
                    previousPressPos = current
                    previousMoveDownPos = current
                }
                PointerEventType.Release -> {
                    if (!isInTouchMode) {
                        if (timeMs() - previousPressTime < Config.CLICK_DURATION_MS) {
                            val previous = previousPressPos
                            if (current != null && previous != null) {
                                if (current.distanceTo(previous) < Config.CLICK_AREA_RADIUS_PX) {
                                    onClick(current.toPt())
                                }
                            }
                        }
                    }
                    previousPressTime = timeMs()
                    previousMoveDownPos = null
                }
            }
        }
    }

    val transformableState = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        previousMoveDownPos = null
        onMove(offsetChange.x.roundToInt(), offsetChange.y.roundToInt())
        onZoom(null, zoomChange.toDouble() - 1)
    }

    fun Modifier.applyTouchScreenHandlers(): Modifier {
        return transformable(
            transformableState
        ).pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    if (timeMs() - previousPressTime < Config.CLICK_DURATION_MS) {
                        val previous = previousPressPos
                        if (previous != null && previous.distanceTo(it) < Config.CLICK_AREA_RADIUS_PX) {
                            onClick(it.toPt())
                        }
                    }
                    previousPressTime = timeMs()
                    previousMoveDownPos = null
                }
            )
        }
    }

    Canvas(
        modifier.applyPointerInput()
            .run {
                if (isInTouchMode) {
                    applyTouchScreenHandlers()
                } else {
                    this
                }
            }
    ) {
        updateSize(size.width.toInt(), size.height.toInt())
        clipRect {
            tiles.forEach { (t, img) ->
                if (img != null) {
                    val size = IntSize(t.size, t.size)
                    val position = IntOffset(t.x, t.y)
                    drawImage(
                        img.extract(),
                        srcOffset = IntOffset(img.offsetX, img.offsetY),
                        srcSize = IntSize(img.cropSize, img.cropSize),
                        dstOffset = position,
                        dstSize = size
                    )
                }
            }
        }
        routes.forEach { route ->
            drawPoints(
                points = route.map { it.toOffset() },
                pointMode = PointMode.Polygon,
                color = Color.Red,
                strokeWidth = 3f
            )
        }
    }

    MarkerComposer(
        markers = markers,
        mapState = mapState,
    )
}

