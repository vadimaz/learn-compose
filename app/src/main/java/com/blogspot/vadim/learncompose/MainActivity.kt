package com.blogspot.vadim.learncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blogspot.vadim.learncompose.ui.screens.AddItemRoute
import com.blogspot.vadim.learncompose.ui.screens.ItemsRoute
import com.blogspot.vadim.learncompose.ui.screens.LocalNavController
import com.blogspot.vadim.learncompose.ui.screens.add.AddItemScreen
import com.blogspot.vadim.learncompose.ui.screens.items.ItemsScreen
import com.blogspot.vadim.learncompose.ui.theme.LearnComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavApp()
                }
            }
        }
    }
}

@Composable
fun NavApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = ItemsRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(ItemsRoute) { ItemsScreen() }
            composable(AddItemRoute) { AddItemScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NavAppPreview() {
    LearnComposeTheme {
        NavApp()
    }
}


