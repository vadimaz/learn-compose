package com.blogspot.vadim.learncompose

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface ItemsRepository {

    /**
     * Get the list of items  and listen to all further changes.
     */
    fun getItems(): StateFlow<List<String>>

    /**
     * Add a new item to the list. THe flow returned by [getItems] will
     * be automatically updated.
     */
    fun addItem(item: String)

    /**
     * Remove all items from the list. The flow returned by [getItems] will
     * be automatically updated.
     */
    fun clear()

    companion object {
        fun get(): ItemsRepository = ItemsRepositoryImpl
    }
}

object ItemsRepositoryImpl : ItemsRepository {

    private val items = MutableStateFlow(getFakeItems())

    override fun getItems(): StateFlow<List<String>> {
        return items
    }

    override fun addItem(item: String) {
        items.update { it + item }
    }

    override fun clear() {
        items.update { emptyList() }
    }

    private fun getFakeItems() = List(10) {
        "Item #$it"
    }
}