package com.blogspot.vadim.learncompose.ui.screens.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.blogspot.vadim.learncompose.R
import com.blogspot.vadim.learncompose.components.ItemDetails
import com.blogspot.vadim.learncompose.components.ItemDetailsState
import com.blogspot.vadim.learncompose.ui.screens.action.ActionScreen
import com.blogspot.vadim.learncompose.ui.screens.edit.EditItemViewModel.ScreenState

@Composable
fun EditItemScreen(index: Int) {
    val viewModel = hiltViewModel<EditItemViewModel, EditItemViewModel.Factory> { factory ->
        factory.create(index)
    }

    ActionScreen(
        delegate = viewModel,
        content = { (screenState, onExecuteAction) ->
            EditItemContent(
                state = screenState,
                onEditButtonClicked = onExecuteAction
            )
        }
    )
}

@Composable
private fun EditItemContent(
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