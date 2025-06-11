package com.blogspot.vadim.learncompose.ui.screens.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.vadim.learncompose.model.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    itemsRepository: ItemsRepository
): ViewModel() {

    val stateFlow: StateFlow<ScreenState> = itemsRepository.getItems()
        .map(ScreenState::Success)
        .stateIn(viewModelScope, SharingStarted.Lazily, ScreenState.Loading)

    sealed class ScreenState {
        data object Loading: ScreenState()
        data class Success(val items: List<String>): ScreenState()
    }

}