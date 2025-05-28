package com.blogspot.vadim.learncompose.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.blogspot.vadim.learncompose.ui.FloatingAction

@Composable
fun AppFloatingActionButton(
    floatingAction: FloatingAction?,
    modifier: Modifier = Modifier
) {
    if (floatingAction != null) {
        FloatingActionButton(
            onClick = floatingAction.onClick,
            modifier = modifier
        ) {
            Icon(
                imageVector = floatingAction.icon,
                contentDescription = null
            )
        }
    }
}