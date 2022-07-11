package com.map

import com.map.collection.Marker

data class BaseStationMarker(
    private val _position: GeoPt,
    val baseStations: List<BaseStation>
) : Marker(_position)
