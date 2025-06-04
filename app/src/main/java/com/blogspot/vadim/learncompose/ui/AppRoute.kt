package com.blogspot.vadim.learncompose.ui

import com.blogspot.vadim.learncompose.ui.AppRoute.Tab
import com.blogspot.vadim.learncompose.ui.screens.item.ItemScreenArgs
import com.blogspot.vadim.learncompose.ui.screens.items.ItemsScreenProducer
import com.blogspot.vadim.learncompose.ui.screens.ProfileScreenProducer
import com.blogspot.vadim.learncompose.ui.screens.SettingsScreenProducer
import com.blogspot.vadim.learncompose.ui.screens.item.itemScreenProducer
import com.blogspot.vadim.navigation.Route
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

sealed class AppRoute(
    override val screenProducer: () -> AppScreen,
) : Route {
    @Parcelize
    data class Item(val args: ItemScreenArgs) : AppRoute(itemScreenProducer(args))

    sealed class Tab(
        screenProducer: () -> AppScreen,
    ) : AppRoute(screenProducer) {
        @Parcelize
        data object Items : Tab(ItemsScreenProducer)

        @Parcelize
        data object Settings : Tab(SettingsScreenProducer)

        @Parcelize
        data object Profile : Tab(ProfileScreenProducer)
    }
}

val RootTabs = persistentListOf(Tab.Items, Tab.Settings, Tab.Profile)