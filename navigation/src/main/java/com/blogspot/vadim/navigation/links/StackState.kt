package com.blogspot.vadim.navigation.links

import com.blogspot.vadim.navigation.Route

data class StackState(
    val routes: List<Route>
) {

    fun withNewRoute(route: Route): StackState = copy(
        routes = routes + route
    )
}