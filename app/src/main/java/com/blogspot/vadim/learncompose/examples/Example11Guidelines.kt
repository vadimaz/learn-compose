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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.blogspot.vadim.learncompose.Rectangle

@Preview(showSystemUi = true)
@Composable
fun Example11Guidelines(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        val startGuideline = createGuidelineFromStart(32.dp)
        val endGuideline = createGuidelineFromEnd(32.dp)
        val topGuideline = createGuidelineFromTop(.05f)
        val bottomGuideline = createGuidelineFromBottom(.05f)

        Rectangle(
            color = Color.Blue,
            modifier = Modifier.constrainAs(createRef()) {
                top.linkTo(topGuideline)
                bottom.linkTo(bottomGuideline)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )
    }
}