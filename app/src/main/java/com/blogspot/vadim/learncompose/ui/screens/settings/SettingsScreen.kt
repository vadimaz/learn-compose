package com.blogspot.vadim.learncompose.ui.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.blogspot.vadim.learncompose.R
import com.blogspot.vadim.learncompose.ui.theme.LearnComposeTheme

@Composable
fun SettingsScreen() {
    Text(
        text = stringResource(R.string.settings_screen),
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
}

@Preview(showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    LearnComposeTheme {
        SettingsScreen()
    }
}