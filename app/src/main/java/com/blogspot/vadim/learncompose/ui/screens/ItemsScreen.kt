package com.blogspot.vadim.learncompose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blogspot.vadim.learncompose.ItemsRepository
import com.blogspot.vadim.learncompose.R
import com.blogspot.vadim.learncompose.ui.AppRoute
import com.blogspot.vadim.learncompose.ui.AppScreen
import com.blogspot.vadim.learncompose.ui.AppScreenEnvironment
import com.blogspot.vadim.learncompose.ui.FloatingAction
import com.blogspot.vadim.learncompose.ui.theme.LearnComposeTheme
import com.blogspot.vadim.navigation.LocalRouter
import com.blogspot.vadim.navigation.ResponseListener
import com.blogspot.vadim.navigation.Router

val ItemsScreenProducer = { ItemsScreen() }

class ItemsScreen : AppScreen {
    private var router: Router? = null

    override val environment = AppScreenEnvironment().apply {
        titleRes = R.string.items
        icon = Icons.AutoMirrored.Filled.List
        floatingAction = FloatingAction(
            icon = Icons.Default.Add,
            onClick = {
                router?.launch(AppRoute.Item(ItemScreenArgs.Add))
            }
        )
    }

    @Composable
    override fun Content() {
        router = LocalRouter.current
        val itemRepository = ItemsRepository.get()
        val items by itemRepository.getItems().collectAsStateWithLifecycle()
        val isEmpty by remember {
            derivedStateOf { items.isEmpty() }
        }
        ResponseListener<ItemScreenResponse> { response ->
            if (response.args is ItemScreenArgs.Edit) {
                itemRepository.updateItem(response.args.index, response.newValue)
            } else {
                itemRepository.addItem(response.newValue)
            }
        }
        ItemsContent(
            isEmpty = isEmpty,
            items = { items },
            onItemClicked = { index ->
                router?.launch(AppRoute.Item(ItemScreenArgs.Edit(index)))
            }
        )
    }
}

@Composable
fun ItemsContent(
    isEmpty: Boolean,
    items: () -> List<String>,
    onItemClicked: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (isEmpty) {
            Text(
                text = stringResource(R.string.no_items),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                val itemList = items()
                items(itemList.size) { index ->
                    Text(
                        text = itemList[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClicked(index)
                            }
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ItemsPreview() {
    val items by ItemsRepository.get()
        .getItems()
        .collectAsStateWithLifecycle()

    LearnComposeTheme {
        ItemsContent(
            isEmpty = items.isEmpty(),
            items = { items },
            onItemClicked = {}
        )
    }
}