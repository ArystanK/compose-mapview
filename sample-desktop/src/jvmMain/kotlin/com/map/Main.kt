package com.map

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

const val ANIMATE = false

fun main() = application {
    val icon = painterResource("images/ic_imageviewer_round.png")
    Window(
        onCloseRequest = ::exitApplication,
        title = "Map View",
        state = WindowState(
            position = WindowPosition(Alignment.TopStart),
            size = getPreferredWindowSize(1200, 600)
        ),
        icon = icon
    ) {
        if (ANIMATE) {
            AnimatedMapView()
        } else {
            MapView(
                modifier = Modifier.fillMaxSize(),
                latitude = 51.12589,
                longitude = 71.43448,
                startScale = 1000.0,
                onMapViewClick = { latitude, longitude ->
                    println("click on geo coordinates: (lat $latitude, lon $longitude)")
                    true
                },
                markers = listOf(
                    MarkerData(
                        id = "1",
                        lon = 71.434592,
                        lat = 51.125354,
                        c = {
                            var infoShown by remember { mutableStateOf(false) }
                            Row(modifier = Modifier.clickable { infoShown = !infoShown }) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource("drawable/base_station_ic.svg"),
                                        contentDescription = "Base station icon",
                                        modifier = Modifier.padding(8.dp),
                                        tint = Color.Cyan
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(24.dp))
                                        .background(Color.Cyan),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "4123",
                                        modifier = Modifier.padding(8.dp),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    )
                )
            )
        }
    }
}

@Composable
fun AnimatedMapView() {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedScale: Float by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 4200f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 5_000
                2f at 500
                100f at 2000
                4100f at 4_500
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedMapState = derivedStateOf {
        MapState(
            latitude = 59.999394,
            longitude = 29.745412,
            scale = animatedScale.toDouble()
        )
    }
    MapView(
        modifier = Modifier.fillMaxSize(),
        state = animatedMapState,
        onStateChange = {},
        markers = listOf(
            MarkerData(
                id = "1",
                lon = 71.434592,
                lat = 51.125354,
                c = {
                    var infoShown by remember { mutableStateOf(false) }
                    Row(modifier = Modifier.clickable { infoShown = !infoShown }) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource("drawable/base_station_ic.svg"),
                                contentDescription = "Base station icon",
                                modifier = Modifier.padding(8.dp),
                                tint = Color.Cyan
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(24.dp))
                                .background(Color.Cyan),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "4123",
                                modifier = Modifier.padding(8.dp),
                                color = Color.White
                            )
                        }
                    }
                }
            )
        )
    )
}
