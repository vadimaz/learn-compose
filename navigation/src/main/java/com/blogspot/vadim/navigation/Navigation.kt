package com.blogspot.vadim.navigation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalContext
import com.blogspot.vadim.navigation.internal.InternalNavigationState
import com.blogspot.vadim.navigation.internal.RouteRecord
import com.blogspot.vadim.navigation.internal.ScreenMultiStack
import com.blogspot.vadim.navigation.internal.ScreenStack
import com.blogspot.vadim.navigation.links.DeepLinkHandler
import com.blogspot.vadim.navigation.links.MultiStackState
import com.blogspot.vadim.navigation.links.StackState
import kotlinx.collections.immutable.ImmutableList

@Stable
data class Navigation internal constructor(
    val router: Router,
    val navigationState: NavigationState,
    internal val internalNavigationState: InternalNavigationState
)

@Composable
fun rememberNavigation(
    rootRoutes: ImmutableList<Route>,
    initialIndex: Int = 0,
    deepLinkHandler: DeepLinkHandler = DeepLinkHandler.DEFAULT
): Navigation {
    val activity = LocalActivity.current
    val screenStack = rememberSaveable(rootRoutes) {
        val inputState = MultiStackState(
            activeStackIndex = initialIndex,
            stacks = rootRoutes.map { rootRoute -> StackState(listOf(rootRoute)) }
        )
        val outputState = activity?.intent?.data?.let { deepLinkUri ->
            deepLinkHandler.handleDeepLink(deepLinkUri, inputState)
        } ?: inputState

        ScreenMultiStack(
            initialIndex = outputState.activeStackIndex,
            stacks = outputState.stacks.map { stackState ->
                ScreenStack(stackState.routes)
            }.toMutableStateList()
        )
    }
    return remember(rootRoutes) {
        Navigation(
            router = screenStack,
            navigationState = screenStack,
            internalNavigationState = screenStack
        )
    }
}