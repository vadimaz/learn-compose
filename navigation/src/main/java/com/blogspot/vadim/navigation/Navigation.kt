package com.blogspot.vadim.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.blogspot.vadim.navigation.internal.InternalNavigationState
import com.blogspot.vadim.navigation.internal.RouteRecord
import com.blogspot.vadim.navigation.internal.ScreenMultiStack
import com.blogspot.vadim.navigation.internal.ScreenStack
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
    initialIndex: Int = 0
): Navigation {
    val screenStack = rememberSaveable(rootRoutes) {
        val stacks = SnapshotStateList<ScreenStack>()
        stacks.addAll(rootRoutes.map(::ScreenStack))
        ScreenMultiStack(stacks, initialIndex)
    }
    return remember(rootRoutes) {
        Navigation(
            router = screenStack,
            navigationState = screenStack,
            internalNavigationState = screenStack
        )
    }
}