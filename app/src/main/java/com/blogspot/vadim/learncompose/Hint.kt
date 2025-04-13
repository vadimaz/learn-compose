package com.blogspot.vadim.learncompose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope

@Composable
fun ConstraintLayoutScope.Hint(
    id: ConstrainedLayoutReference,
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 13.sp,
        modifier = modifier.constrainAs(createRef()) {
            centerHorizontallyTo(id)
            bottom.linkTo(id.top, margin = 2.dp)
        }
    )
}