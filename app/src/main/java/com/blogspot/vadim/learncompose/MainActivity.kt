package com.blogspot.vadim.learncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.blogspot.vadim.learncompose.ui.components.AppScaffold
import com.blogspot.vadim.learncompose.ui.theme.LearnComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnComposeTheme {
                AppScaffold()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AppScreenPreview() {
    LearnComposeTheme {
        AppScaffold()
    }
}