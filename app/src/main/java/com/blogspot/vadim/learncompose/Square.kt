package com.blogspot.vadim.learncompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun Square(color: Color = Color.Red, size: Dp = 150.dp, modifier: Modifier = Modifier) {
    Rectangle(
        color = color,
        width = size,
        height = size,
        modifier = modifier
    )
}

@Preview
@Composable
fun Rectangle(
    color: Color = Color.Gray,
    width: Dp = 200.dp,
    height: Dp = 100.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
        .size(width, height)
        .background(color = color)
    )
}