package com.blogspot.vadim.learncompose.ui.screens.item

import androidx.lifecycle.ViewModel
import com.blogspot.vadim.learncompose.ItemsRepository
import com.blogspot.vadim.learncompose.ui.screens.item.ItemScreenArgs
import com.blogspot.vadim.learncompose.ui.screens.item.ItemScreenResponse

class ItemViewModel(
    private val args: ItemScreenArgs,
    private val repository: ItemsRepository = ItemsRepository.get()
): ViewModel() {

    init {
        println("AAAA ItemViewModel-${hashCode()} created")
    }

    fun getInitialValue(): String {
        return when (args) {
            is ItemScreenArgs.Add -> ""
            is ItemScreenArgs.Edit -> repository.getItems().value[args.index]
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("AAAA ItemViewModel-${hashCode()} destroyed")
    }
}