package com.blogspot.vadim.learncompose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.blogspot.vadim.learncompose.components.AppNavigationBar
import com.blogspot.vadim.learncompose.components.AppToolbar
import com.blogspot.vadim.learncompose.components.NavigateUpAction
import com.blogspot.vadim.learncompose.ui.screens.ItemsGraph
import com.blogspot.vadim.learncompose.ui.screens.ItemsGraph.AddItemRoute
import com.blogspot.vadim.learncompose.ui.screens.ItemsGraph.EditItemRoute
import com.blogspot.vadim.learncompose.ui.screens.ItemsGraph.ItemsRoute
import com.blogspot.vadim.learncompose.ui.screens.LocalNavController
import com.blogspot.vadim.learncompose.ui.screens.MainTabs
import com.blogspot.vadim.learncompose.ui.screens.ProfileGraph
import com.blogspot.vadim.learncompose.ui.screens.ProfileGraph.ProfileRoute
import com.blogspot.vadim.learncompose.ui.screens.SettingsGraph
import com.blogspot.vadim.learncompose.ui.screens.SettingsGraph.SettingsRoute
import com.blogspot.vadim.learncompose.ui.screens.add.AddItemScreen
import com.blogspot.vadim.learncompose.ui.screens.edit.EditItemScreen
import com.blogspot.vadim.learncompose.ui.screens.items.ItemsScreen
import com.blogspot.vadim.learncompose.ui.screens.profile.ProfileScreen
import com.blogspot.vadim.learncompose.ui.screens.routeClass
import com.blogspot.vadim.learncompose.ui.screens.settings.SettingsScreen
import com.blogspot.vadim.learncompose.ui.theme.LearnComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
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
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val titleRes = when (currentBackStackEntry.routeClass()) {
        ItemsRoute::class -> R.string.items_screen
        AddItemRoute::class -> R.string.add_item_screen
        EditItemRoute::class -> R.string.edit_item_screen
        SettingsRoute::class -> R.string.settings_screen
        ProfileRoute::class -> R.string.profile_screen
        else -> R.string.app_name
    }
    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = titleRes,
                navigateUpAction = if (navController.previousBackStackEntry == null) {
                    NavigateUpAction.Hidden
                } else {
                    NavigateUpAction.Visible(
                        onClick = navController::navigateUp
                    )
                }
            )
        },
        floatingActionButton = {
            if (currentBackStackEntry.routeClass() == ItemsRoute::class) {
                FloatingActionButton(
                    onClick = { navController.navigate(AddItemRoute) },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            AppNavigationBar(
                navController = navController,
                tabs = MainTabs
            )
        }
    ) { paddingValues ->
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            val intentHost = LocalActivity.current?.intent?.data?.host
            val startDestination: Any = when (intentHost) {
                "settings" -> SettingsGraph
                "items" -> ItemsGraph
                else -> ProfileGraph
            }
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                navigation<ItemsGraph>(startDestination = ItemsRoute) {
                    composable<ItemsRoute> { ItemsScreen() }
                    composable<AddItemRoute> { AddItemScreen() }
                    composable<EditItemRoute>(
                        deepLinks = listOf(EditItemRoute.Link)
                    ) { entry ->
                        val route: EditItemRoute = entry.toRoute()
                        EditItemScreen(index = route.index)
                    }
                }
                navigation<SettingsGraph>(
                    startDestination = SettingsRoute,
                    deepLinks = listOf(SettingsGraph.Link)
                ) {
                    composable<SettingsRoute> { SettingsScreen() }
                }
                navigation<ProfileGraph>(startDestination = ProfileRoute) {
                    composable<ProfileRoute> { ProfileScreen() }
                }
            }
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


