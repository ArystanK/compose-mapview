package com.map

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import javax.imageio.ImageIO

@Synchronized
fun mkBitmap(z: Int, x: Int, y: Int): ByteArray {
    val width = 512
    val height = 512

    // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
    // into integer pixels
    val bi = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val ig2 = bi.createGraphics()
    val font = Font("TimesRoman", Font.BOLD, 20)
    ig2.font = font
    val message = "$z  \n ($x, $y)"
    val fontMetrics = ig2.fontMetrics
    val stringWidth = fontMetrics.stringWidth(message)
    val stringHeight = fontMetrics.ascent
    ig2.paint = Color.black
    ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4)
    ig2.drawRect(1, 1, 510, 510)
    ig2.drawOval(3, 3, 3, 3)
    ig2.drawOval(512 - 3, 3, 3, 3)
    ig2.drawOval(3, 512 - 3, 3, 3)
    ig2.drawOval(512 - 3, 512 - 3, 3, 3)

    val tempFile = File("/dev/shm/temp.png")
    if (!tempFile.exists()) {
        tempFile.createNewFile()
    }
    ImageIO.write(bi, "PNG", tempFile)
    tempFile.readBytes()
    return tempFile.readBytes()
}
