package com.blogspot.vadim.learncompose.ui.screens.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blogspot.vadim.learncompose.R
import com.blogspot.vadim.learncompose.components.ItemDetails
import com.blogspot.vadim.learncompose.components.ItemDetailsState
import com.blogspot.vadim.learncompose.components.LoadResultContent
import com.blogspot.vadim.learncompose.model.LoadResult
import com.blogspot.vadim.learncompose.ui.screens.EditItemRoute
import com.blogspot.vadim.learncompose.ui.screens.EventConsumer
import com.blogspot.vadim.learncompose.ui.screens.LocalNavController
import com.blogspot.vadim.learncompose.ui.screens.edit.EditItemViewModel.ScreenState
import com.blogspot.vadim.learncompose.ui.screens.routeClass

@Composable
fun EditItemScreen(index: Int) {
    val viewModel = hiltViewModel<EditItemViewModel, EditItemViewModel.Factory> { factory ->
        factory.create(index)
    }

    val navController = LocalNavController.current
    EventConsumer(viewModel.exitChannel) {
        if (navController.currentBackStackEntry.routeClass() == EditItemRoute::class) {
            navController.popBackStack()
        }
    }

    val loadResult by viewModel.stateFlow.collectAsStateWithLifecycle()
    EditItemScreenContent(
        loadResult = loadResult,
        onEditButtonClicked = viewModel::update
    )
}

@Composable
fun EditItemScreenContent(
    loadResult: LoadResult<ScreenState>,
    onEditButtonClicked: (String) -> Unit
) {
    LoadResultContent(
        loadResult = loadResult,
        content = { screenState ->
            SuccessEditItemContent(
                state = screenState,
                onEditButtonClicked = onEditButtonClicked
            )
        }
    )
}

@Composable
private fun SuccessEditItemContent(
    state: ScreenState,
    onEditButtonClicked: (String) -> Unit
) {
    ItemDetails(
        state = ItemDetailsState(
            loadedItem = state.loadedItem,
            textFieldPlaceholder = stringResource(R.string.edit_item_title),
            actionButtonText = stringResource(R.string.edit),
            isActionInProgress = state.isEditInProgress
        ),
        onActionButtonClicked = onEditButtonClicked,
        modifier = Modifier.fillMaxSize()

    )
}