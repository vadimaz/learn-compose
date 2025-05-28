package com.blogspot.vadim.navigation.internal

import com.blogspot.vadim.navigation.ScreenResponseReceiver
import kotlinx.coroutines.flow.Flow

internal sealed class NavigationEvent {
    data class Removed(val routeRecord: RouteRecord) : NavigationEvent()
}

internal interface InternalNavigationState {
    val currentUuid: String
    val screenResponseReceiver: ScreenResponseReceiver
    fun listen(): Flow<NavigationEvent>
}