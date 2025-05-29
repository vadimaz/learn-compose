package com.blogspot.vadim.navigation.internal

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.os.ParcelCompat
import com.blogspot.vadim.navigation.NavigationState
import com.blogspot.vadim.navigation.Route
import com.blogspot.vadim.navigation.Router
import com.blogspot.vadim.navigation.Screen
import com.blogspot.vadim.navigation.ScreenResponseReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal class ScreenMultiStack(
    private val stacks: SnapshotStateList<ScreenStack>,
    initialIndex: Int
) : NavigationState, Router, InternalNavigationState, Parcelable {
    override var currentStackIndex: Int by mutableIntStateOf(initialIndex)
    private val currentStack: ScreenStack get() = stacks[currentStackIndex]

    constructor(parcel: Parcel) : this(
        stacks = SnapshotStateList<ScreenStack>().also { newList ->
            ParcelCompat.readList(
                parcel,
                newList,
                ScreenStack::class.java.classLoader,
                ScreenStack::class.java
            )
        },
        initialIndex = parcel.readInt()
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(stacks)
        dest.writeInt(currentStackIndex)
    }

    companion object CREATOR : Parcelable.Creator<ScreenMultiStack> {
        override fun createFromParcel(source: Parcel): ScreenMultiStack? {
            return ScreenMultiStack(parcel = source)
        }

        override fun newArray(size: Int): Array<out ScreenMultiStack?> {
            return arrayOfNulls(size)
        }
    }

    private val eventsFlow = MutableSharedFlow<NavigationEvent>(
        extraBufferCapacity = Int.MAX_VALUE
    )

    override val isRoot: Boolean get() = currentStack.isRoot
    override val currentRoute: Route get() = currentStack.currentRoute
    override val currentScreen: Screen get() = currentStack.currentScreen
    override val currentUuid: String get() = currentStack.currentUuid
    override val screenResponseReceiver: ScreenResponseReceiver get() = currentStack.screenResponseReceiver

    override fun launch(route: Route) {
        currentStack.launch(route)
    }

    override fun pop(response: Any?) {
        val removeRouteRecord = currentStack.pop(response)
        if (removeRouteRecord != null) {
            eventsFlow.tryEmit(NavigationEvent.Removed(removeRouteRecord))
        }
    }

    override fun restart(
        routes: List<Route>,
        initialIndex: Int
    ) {
        stacks.flatMap { it.getAllRouteRecords() }
            .map { NavigationEvent.Removed(it) }
            .forEach(eventsFlow::tryEmit)
        stacks.clear()
        stacks.addAll(routes.map(::ScreenStack))
        switchStack(initialIndex)
    }

    override fun switchStack(index: Int) {
        currentStackIndex = index
    }

    override fun listen(): Flow<NavigationEvent> {
        return eventsFlow
    }
}