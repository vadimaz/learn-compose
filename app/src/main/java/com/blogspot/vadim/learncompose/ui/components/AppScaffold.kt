package com.blogspot.vadim.learncompose.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.blogspot.vadim.learncompose.ItemsRepository
import com.blogspot.vadim.learncompose.ui.AppDeepLinkHandler
import com.blogspot.vadim.learncompose.ui.AppScreenEnvironment
import com.blogspot.vadim.learncompose.ui.RootTabs
import com.blogspot.vadim.navigation.NavigationHost
import com.blogspot.vadim.navigation.rememberNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    val itemsRepository: ItemsRepository = ItemsRepository.get()
    val navigation = rememberNavigation(RootTabs, deepLinkHandler = AppDeepLinkHandler)
    val (router, navigationState) = navigation
    val environment = navigationState.currentScreen.environment as AppScreenEnvironment

    Scaffold(
        topBar = {
            AppTopBar(
                titleRes = environment.titleRes,
                isRoot = navigationState.isRoot,
                onBack = router::pop,
                onClear = itemsRepository::clear
            )
        },
        bottomBar = {
            AppNavigationBar(
                currentIndex = navigationState.currentStackIndex,
                onIndexSelected = router::switchStack
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(environment.floatingAction)
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = {}
    ) { paddingValues ->
        NavigationHost(
            navigation = navigation,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}