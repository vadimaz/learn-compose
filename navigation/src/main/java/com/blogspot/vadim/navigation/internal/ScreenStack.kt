package com.blogspot.vadim.navigation.internal

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.os.ParcelCompat
import com.blogspot.vadim.navigation.NavigationState
import com.blogspot.vadim.navigation.Route
import com.blogspot.vadim.navigation.Router
import com.blogspot.vadim.navigation.Screen
import com.blogspot.vadim.navigation.ScreenResponseReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ParcelCreator")
internal class ScreenStack(
    private val routes: SnapshotStateList<RouteRecord>,
    private val screenResponsesBus: ScreenResponsesBus = ScreenResponsesBus()
): NavigationState, Router, InternalNavigationState, Parcelable {

    override val isRoot: Boolean get() = routes.size == 1
    override val currentRoute: Route get() = routes.last().route
    override val currentUuid: String get() = routes.last().uuid
    override val currentScreen: Screen by derivedStateOf {
        currentRoute.screenProducer()
    }

    override val screenResponseReceiver: ScreenResponseReceiver = screenResponsesBus

    private val eventsFlow = MutableSharedFlow<NavigationEvent>(
        extraBufferCapacity = Int.MAX_VALUE
    )

    constructor(parcel: Parcel): this(
        SnapshotStateList<RouteRecord>().also {
            ParcelCompat.readList(
                parcel,
                it,
                RouteRecord::class.java.classLoader,
                RouteRecord::class.java
            )
        }
    )

    override fun launch(route: Route) {
        screenResponsesBus.cleanUp()
        routes.add(RouteRecord(route))
    }

    override fun pop(response: Any?) {
        val removedRoute = routes.removeLastOrNull()
        if (removedRoute != null) {
            eventsFlow.tryEmit(NavigationEvent.Removed(removedRoute))
            if (response != null) {
                screenResponsesBus.send(response)
            }
        }
    }

    override fun restart(route: Route) {
        screenResponsesBus.cleanUp()
        routes.apply {
            routes.forEach {
                eventsFlow.tryEmit(NavigationEvent.Removed(it))
            }
            clear()
            add(RouteRecord(route))
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(routes)
    }

    override fun listen(): Flow<NavigationEvent> {
        return eventsFlow
    }
}