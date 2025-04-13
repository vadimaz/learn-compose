package com.blogspot.vadim.learncompose.examples

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.vadim.learncompose.Square

@Preview(showSystemUi = true, locale = "ar")
@Composable
fun Example04AbsoluteLinksToParent(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        Square(
            modifier = Modifier.constrainAs(createRef()) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )
        Square(
            modifier = Modifier.constrainAs(createRef()) {
                absoluteLeft.linkTo(parent.absoluteLeft)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}