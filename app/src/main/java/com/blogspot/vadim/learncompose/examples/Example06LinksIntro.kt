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
fun Example06LinksIntro(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        val largeSquare = createRef()
        val (
            innerLeftSquare,
            innerRightSquare,
            innerTopSquare,
            innerBottomSquare
        ) = createRefs()
        val (
            outerLeftSquare,
            outerRightSquare,
            outerTopSquare,
            outerBottomSquare
        ) = createRefs()

        Square(
            size = 200.dp,
            modifier = Modifier.constrainAs(largeSquare) {
                centerTo(parent)
            }
        )
        Square(
            size = 50.dp,
            color = Color.Blue,
            modifier = Modifier.constrainAs(innerLeftSquare) {
                centerVerticallyTo(largeSquare)
                start.linkTo(largeSquare.start)
            }
        )
        Square(
            size = 50.dp,
            color = Color.Blue,
            modifier = Modifier.constrainAs(innerTopSquare) {
                centerHorizontallyTo(largeSquare)
                top.linkTo(largeSquare.top)
            }
        )
        Square(
            size = 50.dp,
            color = Color.Blue,
            modifier = Modifier.constrainAs(innerRightSquare) {
                centerVerticallyTo(largeSquare)
                end.linkTo(largeSquare.end)
            }
        )
        Square(
            size = 50.dp,
            color = Color.Blue,
            modifier = Modifier.constrainAs(innerBottomSquare) {
                centerHorizontallyTo(largeSquare)
                bottom.linkTo(largeSquare.bottom)
            }
        )
        // Outer green
        Square(
            size = 70.dp,
            color = Color.Green,
            modifier = Modifier.constrainAs(outerLeftSquare) {
                centerVerticallyTo(largeSquare)
                end.linkTo(largeSquare.start)
            }
        )
        Square(
            size = 70.dp,
            color = Color.Green,
            modifier = Modifier.constrainAs(outerTopSquare) {
                centerHorizontallyTo(largeSquare)
                bottom.linkTo(largeSquare.top)
            }
        )
        Square(
            size = 70.dp,
            color = Color.Green,
            modifier = Modifier.constrainAs(outerRightSquare) {
                centerVerticallyTo(largeSquare)
                start.linkTo(largeSquare.end)
            }
        )
        Square(
            size = 70.dp,
            color = Color.Green,
            modifier = Modifier.constrainAs(outerBottomSquare) {
                centerHorizontallyTo(largeSquare)
                top.linkTo(largeSquare.bottom)
            }
        )
    }
}