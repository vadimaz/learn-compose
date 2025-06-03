package com.blogspot.vadim.navigation.internal

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.core.os.ParcelCompat
import com.blogspot.vadim.navigation.Route
import com.blogspot.vadim.navigation.Screen
import com.blogspot.vadim.navigation.ScreenResponseReceiver
import kotlinx.coroutines.flow.Flow

@SuppressLint("ParcelCreator")
internal class ScreenStack(
    private val routes: SnapshotStateList<RouteRecord>,
    private val screenResponsesBus: ScreenResponsesBus = ScreenResponsesBus()
): Parcelable {

    val isRoot: Boolean get() = routes.size == 1
    val currentRoute: Route get() = routes.last().route
    val currentUuid: String get() = routes.last().uuid
    val currentScreen: Screen by derivedStateOf {
        currentRoute.screenProducer()
    }

    val screenResponseReceiver: ScreenResponseReceiver = screenResponsesBus

    constructor(routes: List<Route>) : this(
        routes = routes.map(::RouteRecord).toMutableStateList()
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

    constructor(rootRoute: Route): this(
        routes = mutableStateListOf(RouteRecord(rootRoute))
    )

    fun launch(route: Route) {
        screenResponsesBus.cleanUp()
        routes.add(RouteRecord(route))
    }

    fun pop(response: Any?): RouteRecord? {
        val removedRouteRecord = routes.removeLastOrNull()
        if (removedRouteRecord != null) {
            if (response != null) {
                screenResponsesBus.send(response)
            }
        }
        return removedRouteRecord
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(routes)
    }

    fun getAllRouteRecords(): List<RouteRecord> = routes
}