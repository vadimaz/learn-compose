package com.blogspot.vadim.learncompose.ui.screens.add

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blogspot.vadim.learncompose.R
import com.blogspot.vadim.learncompose.components.ItemDetails
import com.blogspot.vadim.learncompose.components.ItemDetailsState
import com.blogspot.vadim.learncompose.ui.screens.AddItemRoute
import com.blogspot.vadim.learncompose.ui.screens.EventConsumer
import com.blogspot.vadim.learncompose.ui.screens.LocalNavController
import com.blogspot.vadim.learncompose.ui.screens.add.AddItemViewModel.ScreenState
import com.blogspot.vadim.learncompose.ui.screens.routeClass

@Composable
fun AddItemScreen() {
    val viewModel: AddItemViewModel = hiltViewModel()
    val screenState by viewModel.stateFlow.collectAsStateWithLifecycle()
    AddItemContent(
        screenState = screenState,
        onAddButtonClicked = viewModel::add
    )
    val navController = LocalNavController.current
    EventConsumer(viewModel.exitChannel) {
        if (navController.currentBackStackEntry.routeClass() == AddItemRoute::class) {
            navController.popBackStack()
        }
    }
}

@Composable
fun AddItemContent(
    screenState: ScreenState,
    onAddButtonClicked: (String) -> Unit
) {
    ItemDetails(
        state = ItemDetailsState(
            loadedItem = "",
            textFieldPlaceholder = stringResource(R.string.enter_new_item),
            actionButtonText = stringResource(R.string.add),
            isActionInProgress = screenState.isProgressVisible
        ),
        onActionButtonClicked = onAddButtonClicked,
        modifier = Modifier.fillMaxSize()

    )
}