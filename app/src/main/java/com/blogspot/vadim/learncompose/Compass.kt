package com.blogspot.vadim.learncompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalTextApi::class)
@Composable
fun Compass(
    modifier: Modifier = Modifier,
    currentDegree: Float = 0f,
    multiplier: Float = 0.5f,  // wide-angle camera
    textMeasurer: TextMeasurer = rememberTextMeasurer()
) {
    val visibleRange = 90f.div(multiplier)

    Column {
        Canvas(
            modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.Black)
        ) {
            val width = size.width  // canvas width
            val height = size.height  // canvas height

            val degreeWidth = width / visibleRange

            val startsAtDegree = currentDegree - visibleRange / 2
            val endsAtDegree = currentDegree + visibleRange / 2
            var x = 0f

            drawLine(
                color = Color.Green,
                start = Offset(center.x, 0f),
                end = Offset(center.x, center.y),
                strokeWidth = 1.dp.toPx(),
                cap = StrokeCap.Round
            )

            for (degree in startsAtDegree.toInt()..endsAtDegree.toInt() step 1) {
                val degreeFix = when {
                    degree < 0 -> 360 + degree
                    degree >= 360 -> degree - 360
                    else -> degree
                }
                when {
                    degreeFix % 10 == 0 -> {
                        drawLine(
                            color = Color.White,
                            start = Offset(x, height - 15.dp.toPx()),
                            end = Offset(x, height - 1.dp.toPx()),
                            strokeWidth = 1.dp.toPx()
                        )
                        if (degreeFix % 30 == 0) {
                            val text = buildAnnotatedString {
                                withStyle(SpanStyle(fontSize = 10.sp, color = Color.White)) {
                                    append(degreeFix.toString())
                                }
                            }
                            val dimensions = textMeasurer.measure(text)
                            drawText(
                                textMeasurer = textMeasurer,
                                text = text,
                                maxLines = 1,
                                topLeft = Offset(x - dimensions.size.width / 2, 20.dp.toPx())
                            )
                        }
                    }

                    degree % 5 == 0 -> {
                        drawCircle(
                            color = Color.White,
                            radius = 2.dp.toPx(),
                            center = Offset(x, height - 8.dp.toPx()),
                        )
                    }

                    else -> Unit
                }
                val destinations = listOf(0, 45, 90, 135, 180, 225, 270, 315)
                if (degreeFix in destinations) {
                    val destinationText = getDestinationText(degreeFix)
                    val dimensions = textMeasurer.measure(destinationText)
                    drawText(
                        textMeasurer = textMeasurer,
                        text = destinationText,
                        maxLines = 1,
                        topLeft = Offset(x - dimensions.size.width / 2, 2.5.dp.toPx())
                    )
                }
                x += degreeWidth
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.White)
        )
    }
}

private fun getDestinationText(value: Int): AnnotatedString {
    return buildAnnotatedString {
        withStyle(SpanStyle(fontSize = 15.sp, color = Color.White)) {
            append(
                when (value) {
                    45 -> "NE"
                    90 -> "E"
                    135 -> "SE"
                    180 -> "S"
                    225 -> "SW"
                    270 -> "W"
                    315 -> "NW"
                    else -> "N"
                }
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Preview(showBackground = true)
@Composable
fun CompassPreview() {
    Compass(
        currentDegree = 212f
    )
}