package com.blogspot.vadim.learncompose.examples

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.vadim.learncompose.Square

@Preview(showSystemUi = true)
@Composable
fun Example00Default(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier) {
        Square(
            color = Color.Red,
            size = 200.dp
        )
        Square(
            color = Color.Blue,
            size = 150.dp
        )
        Square(
            color = Color.Green,
            size = 100.dp
        )
    }
}