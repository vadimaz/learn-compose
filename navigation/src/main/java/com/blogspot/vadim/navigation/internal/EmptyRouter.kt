package com.blogspot.vadim.navigation.internal

import com.blogspot.vadim.navigation.Route
import com.blogspot.vadim.navigation.Router

internal object EmptyRouter: Router {
    override fun launch(route: Route) = Unit

    override fun pop(response: Any?) = Unit

    override fun restart(
        routes: List<Route>,
        initialIndex: Int
    ) = Unit

    override fun switchStack(index: Int) = Unit
}