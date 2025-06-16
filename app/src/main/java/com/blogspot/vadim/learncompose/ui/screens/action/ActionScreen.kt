package com.blogspot.vadim.learncompose.ui.screens.action

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blogspot.vadim.learncompose.components.LoadResultContent
import com.blogspot.vadim.learncompose.ui.screens.EventConsumer
import com.blogspot.vadim.learncompose.ui.screens.LocalNavController
import com.blogspot.vadim.learncompose.ui.screens.action.ActionViewModel.Delegate
import com.blogspot.vadim.learncompose.ui.screens.routeClass

data class ActionContentState<State, Action>(
    val state: State,
    val onExecuteAction: (Action) -> Unit
)

@Composable
fun <State, Action> ActionScreen(
    delegate: Delegate<State, Action>,
    content: @Composable (ActionContentState<State, Action>) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<ActionViewModel<State, Action>> {
        ActionViewModel(delegate)
    }
    val navController = LocalNavController.current
    val rememberedScreenRoute = remember {
        navController.currentBackStackEntry.routeClass()
    }
    EventConsumer(channel = viewModel.exitChannel) {
        if (rememberedScreenRoute == navController.currentBackStackEntry.routeClass()) {
            navController.popBackStack()
        }
    }

    val loadResult by viewModel.stateFlow.collectAsStateWithLifecycle()
    LoadResultContent(
        loadResult = loadResult,
        content = { state ->
            val actionContentState = ActionContentState(
                state = state,
                onExecuteAction = viewModel::execute
            )
            content(actionContentState)
        },
        modifier = modifier
    )
}