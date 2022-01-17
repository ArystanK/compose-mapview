package com.map

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.CompletableDeferred
import kotlin.random.Random

fun createRealRepository(ktorClient: HttpClient) = object : TileContentRepository<ByteArray> {
    override suspend fun getTileContent(tile: Tile): ByteArray {
        println("LOAD START $tile ${System.currentTimeMillis() % 1000000}")
        if (Random.nextInt(3) == 1) {
            throw Exception("check network")
        }
        val result = ktorClient.get<ByteArray>(tile.tileUrl)
        println("LOAD END $tile ${System.currentTimeMillis() % 1000000}")
        return result
    }
}

