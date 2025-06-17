package com.blogspot.vadim.learncompose.ui.screens.add

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.blogspot.vadim.learncompose.R
import com.blogspot.vadim.learncompose.components.ItemDetails
import com.blogspot.vadim.learncompose.components.ItemDetailsState
import com.blogspot.vadim.learncompose.ui.screens.action.ActionScreen
import com.blogspot.vadim.learncompose.ui.screens.add.AddItemViewModel.ScreenState

@Composable
fun AddItemScreen() {
    val viewModel: AddItemViewModel = hiltViewModel()
    ActionScreen(
        delegate = viewModel,
        content = { (screenState, onExecuteAction) ->
            AddItemContent(
                screenState = screenState,
                onAddButtonClicked = onExecuteAction
            )
        }
    )
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