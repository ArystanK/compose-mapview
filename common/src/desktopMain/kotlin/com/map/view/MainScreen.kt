package com.map.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.map.ResString
import com.map.model.ContentState
import com.map.model.Picture
import com.map.style.*
import com.map.utils.toByteArray

@Composable
fun MainScreen(content: ContentState) {
    Column {
        ScrollableArea(content)
    }
}

@Composable
fun Miniature(
    picture: Picture,
    content: ContentState
) {
    val infoButtonHover = remember { mutableStateOf(false) }
    Card(
        backgroundColor = MiniatureColor,
        modifier = Modifier.padding(start = 10.dp, end = 18.dp).height(150.dp)
            .fillMaxWidth(),
        shape = RectangleShape
    ) {
        Row(modifier = Modifier.padding(end = 30.dp)) {
            Clickable(
                onClick = {

                }
            ) {
                Image(
                    org.jetbrains.skia.Image.makeFromEncoded(
                        toByteArray(picture.image)
                    ).asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.height(70.dp)
                        .width(90.dp)
                        .padding(start = 1.dp, top = 1.dp, end = 1.dp, bottom = 1.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = picture.name,
                color = Foreground,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp),
                style = MaterialTheme.typography.body1
            )

            Clickable(
                modifier = Modifier.height(70.dp)
                    .width(30.dp)
                    .background(color = if (infoButtonHover.value) TranslucentWhite else Transparent),
                onClick = {
                    showPopUpMessage(
                        "${ResString.picture} " +
                        "${picture.name} \n" +
                        "${ResString.size} " +
                        "${picture.width}x${picture.height} " +
                        "${ResString.pixels}"
                    )
                }
            ) {
                Image(
                    icDots(),
                    contentDescription = null,
                    modifier = Modifier.height(70.dp)
                        .width(30.dp)
                        .padding(start = 1.dp, top = 25.dp, end = 1.dp, bottom = 25.dp),
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}

@Composable
fun ScrollableArea(content: ContentState) {
    val state by content.stateFlow.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
        .padding(end = 8.dp)
    ) {
        val stateVertical = rememberScrollState(0)
        Column(modifier = Modifier.verticalScroll(stateVertical)) {
            var index = 1
            Column {
                for (picture in state.pictures) {
                    Miniature(
                        picture = picture,
                        content = content
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    index++
                }
            }
        }
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(stateVertical),
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight()
        )
    }
}