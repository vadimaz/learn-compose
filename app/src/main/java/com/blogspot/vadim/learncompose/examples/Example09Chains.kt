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
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.blogspot.vadim.learncompose.Hint
import com.blogspot.vadim.learncompose.Rectangle
import com.blogspot.vadim.learncompose.Square

@Preview(showSystemUi = true)
@Composable
fun Example09Chains(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        val (
            square1,
            square2,
            square3,
            square4,
            square5
        ) = createRefs()

        val blackLine = createRef()


        Square(
            size = 30.dp,
            color = Color.Red,
            modifier = Modifier.constrainAs(square1) {
                centerVerticallyTo(parent)
                horizontalChainWeight = 1f
                width = Dimension.fillToConstraints
            }
        )
        Square(
            size = 30.dp,
            color = Color.Green,
            modifier = Modifier.constrainAs(square2) {
                centerVerticallyTo(parent)
                //horizontalChainWeight = 1f
            }
        )
        Square(
            size = 30.dp,
            color = Color.Blue,
            modifier = Modifier.constrainAs(square3) {
                centerVerticallyTo(parent)
                //horizontalChainWeight = 1f
            }
        )
        Square(
            size = 30.dp,
            color = Color.Yellow,
            modifier = Modifier.constrainAs(square4) {
                centerVerticallyTo(parent)
                //horizontalChainWeight = 1f
            }
        )
        Square(
            size = 30.dp,
            color = Color.Magenta,
            modifier = Modifier.constrainAs(square5) {
                centerVerticallyTo(parent)
                //horizontalChainWeight = 1f
            }
        )

        val chain = createHorizontalChain(
            square1, square2, square3, square4, square5,
            chainStyle = ChainStyle.SpreadInside
        )

        constrain(chain) {
            start.linkTo(blackLine.start)
            end.linkTo(blackLine.end)
        }

        Rectangle(
            width = 300.dp,
            height = 10.dp,
            color = Color.Black,
            modifier = Modifier.constrainAs(blackLine) {
                height = Dimension.value(10.dp)
                width = Dimension.fillToConstraints
                top.linkTo(square1.bottom, margin = 10.dp)
                start.linkTo(parent.start, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
            }
        )
    }
}