package com.blogspot.vadim.navigation.internal

import com.blogspot.vadim.navigation.Route
import java.util.UUID

internal data class RouteRecord(
    val uuid: String,
    val route: Route
) {
    constructor(route: Route): this(
        uuid = UUID.randomUUID().toString(),
        route = route
    )
}