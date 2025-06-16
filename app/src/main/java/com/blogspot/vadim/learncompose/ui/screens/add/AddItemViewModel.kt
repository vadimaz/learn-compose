package com.blogspot.vadim.learncompose.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.vadim.learncompose.model.ItemsRepository
import com.blogspot.vadim.learncompose.ui.screens.action.ActionViewModel
import com.blogspot.vadim.learncompose.ui.screens.action.ActionViewModel.Delegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
): ViewModel(), Delegate<AddItemViewModel.ScreenState, String> {

    override suspend fun loadState(): ScreenState {
        return ScreenState()
    }

    override fun showProgress(input: ScreenState): ScreenState {
        return input.copy(isProgressVisible = true)
    }

    override suspend fun execute(action: String) {
        itemsRepository.add(action)
    }

    data class ScreenState(
        val isProgressVisible: Boolean = false
    )

}