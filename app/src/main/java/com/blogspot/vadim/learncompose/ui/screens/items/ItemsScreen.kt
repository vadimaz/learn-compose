package com.blogspot.vadim.learncompose.ui.screens.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blogspot.vadim.learncompose.components.LoadResultContent
import com.blogspot.vadim.learncompose.model.LoadResult
import com.blogspot.vadim.learncompose.ui.screens.ItemsGraph
import com.blogspot.vadim.learncompose.ui.screens.LocalNavController
import com.blogspot.vadim.learncompose.ui.screens.items.ItemsViewModel.ScreenState

@Composable
fun ItemsScreen() {
    val viewModel = hiltViewModel<ItemsViewModel>()
    val loadResult = viewModel.stateFlow.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    ItemsContent(
        getLoadResult = { loadResult.value },
        onItemClicked = { index ->
            navController.navigate(ItemsGraph.EditItemRoute(index))
        }
    )
}

@Composable
fun ItemsContent(
    getLoadResult: () -> LoadResult<ScreenState>,
    onItemClicked: (Int) -> Unit
) {

    LoadResultContent(
        loadResult = getLoadResult(),
        content = { screenState ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(screenState.items) { index, item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .clickable { onItemClicked(index) }
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ItemsPreview() {
    ItemsContent(
        getLoadResult = { LoadResult.Loading },
        onItemClicked = {}
    )
}