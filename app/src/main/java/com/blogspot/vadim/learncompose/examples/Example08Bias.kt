package com.blogspot.vadim.learncompose.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.blogspot.vadim.learncompose.Hint
import com.blogspot.vadim.learncompose.Rectangle
import com.blogspot.vadim.learncompose.Square

@Preview(showSystemUi = true)
@Composable
fun Example08Bias(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        val (
            startBound,
            endBound
        ) = createRefs()

        // bounds
        Rectangle(
            width = 10.dp,
            height = 300.dp,
            color = Color.Black,
            modifier = Modifier.constrainAs(startBound) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start, margin = 40.dp)
            }
        )
        Rectangle(
            width = 10.dp,
            height = 300.dp,
            color = Color.Black,
            modifier = Modifier.constrainAs(endBound) {
                centerVerticallyTo(parent)
                end.linkTo(parent.end, margin = 40.dp)
            }
        )

        Square(
            size = 25.dp,
            modifier = Modifier.constrainAs(createRef()) {
                linkTo(
                    start = startBound.end,
                    end = endBound.start,
                    bias = .8f
                )
                linkTo(
                    top = startBound.top,
                    bottom = startBound.bottom,
                    bias = .8f
                )
            }
        )
    }
}