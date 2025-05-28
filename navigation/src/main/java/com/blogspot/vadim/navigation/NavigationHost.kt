package com.blogspot.vadim.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.blogspot.vadim.navigation.internal.EmptyRouter
import com.blogspot.vadim.navigation.internal.NavigationEvent
import kotlinx.coroutines.flow.filterIsInstance

val LocalRouter = staticCompositionLocalOf<Router> { EmptyRouter }

@Composable
fun NavigationHost(
    navigation: Navigation,
    modifier: Modifier = Modifier
) {
    val (router, navigationState, internalState) = navigation

    BackHandler(enabled = !navigationState.isRoot) {
        router.pop()
    }

    val saveableStateHolder = rememberSaveableStateHolder()
    saveableStateHolder.SaveableStateProvider(internalState.currentUuid) {
        Box(modifier = modifier) {
            CompositionLocalProvider(
                LocalRouter provides router,
                LocalScreenResponseReceiver provides internalState.screenResponseReceiver
            ) {
                navigationState.currentScreen.Content()
            }
        }
        LaunchedEffect(navigation) {
            navigation.internalNavigationState.listen()
                .filterIsInstance<NavigationEvent.Removed>()
                .collect { event ->
                    saveableStateHolder.removeState(event.routeRecord)
                }
        }
    }
}