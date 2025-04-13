package com.blogspot.vadim.learncompose.examples

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.vadim.learncompose.Square

@Preview(showSystemUi = true)
@Composable
fun Example05MarginV2(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        Square(
            modifier = Modifier
                .constrainAs(createRef()) {
                start.linkTo(parent.start, margin = 20.dp)
                top.linkTo(parent.top, margin = 20.dp)
            }
        )
        Square(
            color = Color.Green,
            modifier = Modifier
                .constrainAs(createRef()) {
                end.linkTo(parent.end, margin = 20.dp)
                top.linkTo(parent.top, margin = 20.dp)
            }
        )
        Square(
            color = Color.Yellow,
            modifier = Modifier
                .constrainAs(createRef()) {
                start.linkTo(parent.start, margin = 20.dp)
                bottom.linkTo(parent.bottom, margin = 20.dp)
            }
        )
        Square(
            color = Color.Blue,
            modifier = Modifier
                .constrainAs(createRef()) {
                end.linkTo(parent.end, margin = 20.dp)
                bottom.linkTo(parent.bottom, margin = 20.dp)
            }
        )
    }
}