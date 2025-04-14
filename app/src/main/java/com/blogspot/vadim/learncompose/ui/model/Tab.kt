package com.blogspot.vadim.learncompose.ui.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.blogspot.vadim.learncompose.R

enum class Tab(
    @StringRes val labelRes: Int,
    val icon: ImageVector
) {
    ITEMS(R.string.items, Icons.AutoMirrored.Filled.List),
    SETTINGS(R.string.settings, Icons.Default.Settings),
    PROFILE(R.string.profile, Icons.Default.AccountBox)
}