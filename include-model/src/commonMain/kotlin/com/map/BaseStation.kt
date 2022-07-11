package com.map

data class BaseStation(
    val operator: String,
    val lac: Int,
    val cid: Int,
    val power: Int,
    val address: String,
    val networkType: String,
    val azimuth: Int
)
