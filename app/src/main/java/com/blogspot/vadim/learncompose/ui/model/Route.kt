package com.blogspot.vadim.learncompose.ui.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.blogspot.vadim.learncompose.R
import com.blogspot.vadim.learncompose.ui.model.Route.Tab

sealed class Route(@StringRes open val titleRes: Int) {
    object AddItem : Route(R.string.add_item)

    sealed class Tab(
        @StringRes override val titleRes: Int,
        val icon: ImageVector
    ): Route(titleRes) {
        object Items : Tab(R.string.items, Icons.AutoMirrored.Filled.List)
        object Settings : Tab(R.string.settings, Icons.Default.Settings)
        object Profile : Tab(R.string.profile, Icons.Default.AccountBox)
    }
}

val RootTabs = listOf(Tab.Items, Tab.Settings, Tab.Profile)