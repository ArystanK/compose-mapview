package com.map

import io.ktor.client.*

actual val ktorClient: HttpClient = HttpClient()

actual suspend fun getImage(z: Int, x: Int, y: Int): Picture = downloadImageByCoordinates(z,x,y)
