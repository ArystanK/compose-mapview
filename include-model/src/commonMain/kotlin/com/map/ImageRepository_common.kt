package com.map

interface TileContentRepository<T> {
    suspend fun getTileContent(tile: Tile): T
}

fun <A, B> TileContentRepository<A>.adapter(transform: (A) -> B): TileContentRepository<B> {
    val origin = this
    return object : TileContentRepository<B> {
        override suspend fun getTileContent(tile: Tile): B {
            return transform(origin.getTileContent(tile))
        }
    }
}



