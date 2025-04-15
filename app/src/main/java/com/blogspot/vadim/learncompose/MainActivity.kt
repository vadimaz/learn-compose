package com.blogspot.vadim.learncompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blogspot.vadim.learncompose.ui.model.RootTabs
import com.blogspot.vadim.learncompose.ui.model.Route
import com.blogspot.vadim.learncompose.ui.model.Route.Tab
import com.blogspot.vadim.learncompose.ui.theme.LearnComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnComposeTheme {
                AppScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(itemsRepository: ItemsRepository = ItemsRepository.get()) {
    val items by itemsRepository.getItems().collectAsStateWithLifecycle()
    val stack = remember { mutableStateListOf<Route>(Tab.Items) }
    var currentRoute = stack.last()
    val isRoot by remember { derivedStateOf { stack.size == 1 } }

    BackHandler(enabled = !isRoot) {
        stack.removeAt(stack.lastIndex)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(currentRoute.titleRes),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (!isRoot) {
                                stack.removeAt(stack.lastIndex)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isRoot) {
                                Icons.Default.Menu
                            } else {
                                Icons.AutoMirrored.Filled.ArrowBack
                            },
                            contentDescription = stringResource(R.string.top_bar_navigation_icon_content_desc)
                        )
                    }
                },
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    val context = LocalContext.current
                    IconButton(
                        onClick = {
                            showMenu = !showMenu
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.about))
                            },
                            onClick = {
                                Toast.makeText(context, "About", Toast.LENGTH_SHORT).show()
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.clear))
                            },
                            onClick = {
                                itemsRepository.clear()
                                showMenu = false
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (isRoot) {
                NavigationBar {
                    RootTabs.forEach { tab ->
                        NavigationBarItem(
                            selected = tab == currentRoute,
                            label = { Text(stringResource(tab.titleRes)) },
                            onClick = {
                                stack.clear()
                                stack.add(tab)
                            },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }

            }
        },
        floatingActionButton = {
            if (currentRoute == Tab.Items) {
                FloatingActionButton(
                    onClick = {
                        stack.add(Route.AddItem)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = {}
    ) { paddingValues ->
        val modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()

        when (currentRoute) {
            Tab.Items -> ItemsScreen(items, modifier)
            Tab.Settings -> SettingsScreen(modifier)
            Tab.Profile -> ProfileScreen(modifier)
            Route.AddItem -> AddItemScreen(modifier = modifier) {
                itemsRepository.addItem(it)
                stack.removeAt(stack.lastIndex)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AppScreenPreview() {
    LearnComposeTheme {
        AppScreen()
    }
}

@Composable
fun ItemsScreen(items: List<String>, modifier: Modifier = Modifier) {
    if (items.isEmpty()) {
        Text(
            text = stringResource(R.string.no_items),
            textAlign = TextAlign.Center,
            modifier = modifier.wrapContentHeight()
        )
    } else {
        LazyColumn(
            modifier = modifier
        ) {

            items(items) { item ->
                Text(
                    text = item,
                    modifier = Modifier.padding(12.dp)
                )
            }

        }
    }
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.settings)
        )
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.profile)
        )
    }
}

@Composable
fun AddItemScreen(modifier: Modifier = Modifier, onSubmitNewItem: (String) -> Unit) {
    var newItemValue by remember { mutableStateOf("") }
    val isAddEnabled by remember {
        derivedStateOf { newItemValue.isNotEmpty() }
    }

    Column(
        modifier = modifier,
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
