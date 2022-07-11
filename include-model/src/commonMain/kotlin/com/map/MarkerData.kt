package com.map

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape

class MarkerData(
    val id: String,
    val lon: Double, val lat: Double,
    clipShape: Shape? = null,
    clickableAreaScale: Offset = Offset(1f, 1f),
    clickableAreaCenterOffset: Offset = Offset.Zero,
    val c: @Composable () -> Unit
) {
    var clickableAreaScale by mutableStateOf(clickableAreaScale)
    var clickableAreaCenterOffset by mutableStateOf(clickableAreaCenterOffset)
    var clipShape: Shape? by mutableStateOf(clipShape)
    var absoluteOffset by mutableStateOf(Offset.Zero)

    var measuredWidth = 0
    var measuredHeight = 0

    fun contains(x: Int, y: Int): Boolean {
        val centerX = absoluteOffset.x / 2 + measuredWidth * clickableAreaCenterOffset.x
        val deltaX = measuredWidth * clickableAreaScale.x / 2
        val centerY = absoluteOffset.y + measuredHeight / 2 + measuredHeight * clickableAreaCenterOffset.y
        val deltaY = measuredHeight * clickableAreaScale.y / 2

        return (x >= centerX - deltaX && x <= centerX + deltaX
                && y >= centerY - deltaY && y <= centerY + deltaY)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as MarkerData

        if (id != other.id) return false
        if (absoluteOffset.x != other.absoluteOffset.x) return false
        if (absoluteOffset.y != other.absoluteOffset.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + absoluteOffset.x.hashCode()
        result = 31 * result + absoluteOffset.y.hashCode()
        return result
    }
}