package com.blogspot.vadim.learncompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blogspot.vadim.learncompose.ItemsRepository
import com.blogspot.vadim.learncompose.R
import com.blogspot.vadim.learncompose.ui.AppScreen
import com.blogspot.vadim.learncompose.ui.AppScreenEnvironment
import com.blogspot.vadim.learncompose.ui.theme.LearnComposeTheme
import com.blogspot.vadim.navigation.LocalRouter

class AddItemScreen: AppScreen {
    override val environment: AppScreenEnvironment
        get() = AppScreenEnvironment().apply {
            titleRes = R.string.add_item
        }

    @Composable
    override fun Content() {
        val itemsRepository = ItemsRepository.get()
        val router = LocalRouter.current

        AddItemContent(
            onSubmitNewItem = {
                itemsRepository.addItem(it)
                router.pop()
            }
        )
    }
}

@Composable
fun AddItemContent(onSubmitNewItem: (String) -> Unit) {
    var newItemValue by rememberSaveable { mutableStateOf("") }
    val isAddEnabled by remember {
        derivedStateOf { newItemValue.isNotEmpty() }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = newItemValue,
            label = {
                Text(text = stringResource(R.string.enter_new_value))
            },
            singleLine = true,
            onValueChange = {
                newItemValue = it
            }
        )
        Spacer(Modifier.height(16.dp))
        Button(
            enabled = isAddEnabled,
            onClick = {
                onSubmitNewItem.invoke(newItemValue)
            }
        ) {
            Text(text = stringResource(R.string.add_new_item))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AddItemPreview() {
    LearnComposeTheme {
        AddItemContent(onSubmitNewItem = {})
    }
}