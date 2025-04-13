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
import androidx.constraintlayout.compose.Visibility
import com.blogspot.vadim.learncompose.Hint
import com.blogspot.vadim.learncompose.Rectangle
import com.blogspot.vadim.learncompose.Square

@Preview(showSystemUi = true)
@Composable
fun Example09VisibilityAndGoneMargin(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        val (
            centerSquare,
        ) = createRefs()

        Square(
            size = 100.dp,
            modifier = Modifier.constrainAs(centerSquare) {
                centerTo(parent)
                visibility = Visibility.Gone
            }
        )
        Square(
            size = 50.dp,
            color = Color.Green,
            modifier = Modifier.constrainAs(createRef()) {
                centerHorizontallyTo(centerSquare)
                bottom.linkTo(centerSquare.top)
            }
        )
        Square(
            size = 50.dp,
            color = Color.Blue,
            modifier = Modifier.constrainAs(createRef()) {
                centerHorizontallyTo(centerSquare)
                top.linkTo(
                    centerSquare.bottom,
                    margin = 10.dp,
                    goneMargin = 50.dp
                )
            }
        )
    }
}