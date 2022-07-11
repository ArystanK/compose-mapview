package com.map

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layoutId

@Composable
fun MarkerComposer(
    modifier: Modifier = Modifier,
    markers: List<MarkerData>,
    mapState: InternalMapState,
) {
    MarkerLayout(modifier = modifier, mapState = mapState) {
        for (marker in markers) {
            key(marker.id) {
                Box(
                    modifier = Modifier
                        .layoutId(marker)
                        .then(
                            marker.clipShape?.let {
                                Modifier.clip(it)
                            } ?: Modifier
                        )
                ) {
                    marker.c()
                }
            }
        }
    }
}