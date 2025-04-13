package com.blogspot.vadim.learncompose.examples

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.vadim.learncompose.Square

@Preview(showSystemUi = true)
@Composable
fun Example03LinksToParent(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        Square(
            modifier = Modifier.constrainAs(createRef()) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )
        Square(
            color = Color.Green,
            modifier = Modifier.constrainAs(createRef()) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
        )
        Square(
            color = Color.Yellow,
            modifier = Modifier.constrainAs(createRef()) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }
        )
        Square(
            color = Color.Blue,
            modifier = Modifier.constrainAs(createRef()) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}