package com.blogspot.vadim.learncompose.ui.screens.items

import androidx.lifecycle.ViewModel
import com.blogspot.vadim.learncompose.ItemsRepository
import com.blogspot.vadim.learncompose.ui.screens.item.ItemScreenArgs
import com.blogspot.vadim.learncompose.ui.screens.item.ItemScreenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val repository: ItemsRepository
): ViewModel() {

    val itemsFlow = repository.getItems()

    fun processResponse(response: ItemScreenResponse) {
        when (response.args) {
            is ItemScreenArgs.Add -> repository.addItem(response.newValue)
            is ItemScreenArgs.Edit -> repository.updateItem(response.args.index, response.newValue)
        }
    }
}