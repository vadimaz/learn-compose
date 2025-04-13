package com.blogspot.vadim.learncompose.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.blogspot.vadim.learncompose.Rectangle

@Preview(showSystemUi = true)
@Composable
fun Example12DecoupledApi(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize(),
        constraintSet = constraints()
    ) {


        Text(
            text = "Hello World 12345",
            color = Color.White,
            modifier = Modifier
                .background(Color.Red)
                .padding(6.dp)
                .layoutId("text1")
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .layoutId("space")
        )
        Text(
            text = "Lorem Lorem Lorem Lorem",
            color = Color.White,
            modifier = Modifier
                .background(Color.Red)
                .padding(6.dp)
                .layoutId("text2")
        )


        Rectangle(
            width = 10.dp,
            color = Color.Black,
            modifier = Modifier.layoutId("rectangle")
        )
    }
}

private fun constraints() = ConstraintSet {
    val text1 = createRefFor("text1")
    val space = createRefFor("space")
    val text2 = createRefFor("text2")
    val rectangle = createRefFor("rectangle")

    constrain(text1) {
        centerHorizontallyTo(parent)
    }
    constrain(space) {
        height = Dimension.value(10.dp)
    }
    constrain(text2) {
        centerHorizontallyTo(parent)
    }
    createVerticalChain(text1, space, text2, chainStyle = ChainStyle.Packed)
    val barrier = createEndBarrier(text1, text2)
    constrain(rectangle) {
        centerVerticallyTo(parent)
        end.linkTo(barrier)
    }
}