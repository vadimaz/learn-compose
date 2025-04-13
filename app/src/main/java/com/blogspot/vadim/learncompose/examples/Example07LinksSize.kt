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
fun Example07LinksSize(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier.fillMaxSize()) {
        val (
            startBound,
            endBound,
            rectFillToConstraints,
            rectMatchParent,
            rectPercentage,
            rectWrapContent,
            rectPreferredWrapContent,
            rectPreferredValue,
            rectAspectValue
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

        // fill to constraints
        Hint(rectFillToConstraints, "fill to constraints")
        Rectangle(
            color = Color.Red,
            modifier = Modifier.constrainAs(rectFillToConstraints) {
                start.linkTo(startBound.end)
                end.linkTo(endBound.start)
                top.linkTo(startBound.top, margin = 10.dp)
                width = Dimension.fillToConstraints
                height = Dimension.value(20.dp)
            }
        )

        // match parent
        Hint(rectMatchParent, "match parent")
        Rectangle(
            color = Color.Red,
            modifier = Modifier.constrainAs(rectMatchParent) {
                start.linkTo(startBound.end)
                end.linkTo(endBound.start)
                top.linkTo(rectFillToConstraints.bottom, margin = 30.dp)
                width = Dimension.matchParent
                height = Dimension.value(20.dp)
            }
        )

        // percentage
        Hint(rectPercentage, "percentage")
        Rectangle(
            color = Color.Red,
            modifier = Modifier.constrainAs(rectPercentage) {
                start.linkTo(startBound.end)
                end.linkTo(endBound.start)
                top.linkTo(rectMatchParent.bottom, margin = 30.dp)
                width = Dimension.percent(0.7f)
                height = Dimension.value(20.dp)
            }
        )

        // wrap content
        Hint(rectWrapContent, "wrap content")
        Text(
            "123",
            color = Color.White,
            modifier = Modifier
                .background(Color.Red)
                .padding(horizontal = 6.dp, vertical = 2.dp)
                .constrainAs(rectWrapContent) {
                    start.linkTo(startBound.end)
                    end.linkTo(endBound.start)
                    top.linkTo(rectPercentage.bottom, margin = 30.dp)
                    width = Dimension.wrapContent
                }
        )

        // preferred wrap content
        Hint(rectPreferredWrapContent, "preferred wrap content")
        Text(
            "123 asdasdad asd asd asdasda   asdasdfasdas  asd asd a",
            color = Color.White,
            modifier = Modifier
                .background(Color.Red)
                .padding(horizontal = 6.dp, vertical = 2.dp)
                .constrainAs(rectPreferredWrapContent) {
                    start.linkTo(startBound.end)
                    end.linkTo(endBound.start)
                    top.linkTo(rectWrapContent.bottom, margin = 30.dp)
                    width = Dimension.preferredWrapContent
                }
        )

        // preferred value
        Hint(rectPreferredValue, "preferred value")
        Rectangle(
            color = Color.Red,
            modifier = Modifier.constrainAs(rectPreferredValue) {
                start.linkTo(startBound.end)
                end.linkTo(endBound.start)
                top.linkTo(rectPreferredWrapContent.bottom, margin = 30.dp)
                width = Dimension.preferredValue(200.dp)
                height = Dimension.value(20.dp)
            }
        )

        // aspect ratio
        Hint(rectAspectValue, "aspect ratio")
        Rectangle(
            color = Color.Red,
            modifier = Modifier.constrainAs(rectAspectValue) {
                start.linkTo(startBound.end)
                end.linkTo(endBound.start)
                top.linkTo(rectPreferredValue.bottom, margin = 30.dp)
                width = Dimension.percent(.25f)
                height = Dimension.ratio("2:3")
            }
        )
    }
}