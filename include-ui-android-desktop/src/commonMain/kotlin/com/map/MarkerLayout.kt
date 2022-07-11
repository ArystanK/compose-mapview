package com.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId

@Composable
internal fun MarkerLayout(
    modifier: Modifier,
    mapState: InternalMapState,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) { measurables, constraints ->
        val placeableCst = constraints.copy(minHeight = 0, minWidth = 0)

        layout(mapState.width, mapState.height) {
            for (measurable in measurables) {
                val data = measurable.layoutId as? MarkerData ?: continue
                val placeable = measurable.measure(placeableCst)
                data.measuredWidth = placeable.measuredWidth
                data.measuredHeight = placeable.measuredHeight
                data.absoluteOffset = mapState.geoToDisplay(createGeoPt(data.lat, data.lon)).toOffset()
                placeable.place(data.absoluteOffset.x.toInt(), data.absoluteOffset.y.toInt())
            }
        }
    }
}