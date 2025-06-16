package com.blogspot.vadim.learncompose.ui.screens.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.vadim.learncompose.model.ItemsRepository
import com.blogspot.vadim.learncompose.model.LoadResult
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

    val stateFlow: StateFlow<LoadResult<ScreenState>> = itemsRepository.getItems()
        .map { LoadResult.Success(ScreenState(it)) }
        .stateIn(viewModelScope, SharingStarted.Lazily, LoadResult.Loading)

    data class ScreenState(val items: List<String>)

}