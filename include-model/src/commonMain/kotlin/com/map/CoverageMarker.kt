package com.map

import com.map.collection.Marker

data class CoverageMarker(
    private val _position: GeoPt
) : Marker(_position)
