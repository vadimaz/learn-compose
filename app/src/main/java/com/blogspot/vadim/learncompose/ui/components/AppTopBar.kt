package com.blogspot.vadim.learncompose.ui.components

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.blogspot.vadim.learncompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    titleRes: Int,
    isRoot: Boolean,
    onBack: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(
                    titleRes
                ),
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    if (!isRoot) onBack.invoke()
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
                        onClear.invoke()
                        showMenu = false
                    }
                )
            }
        },
        modifier = modifier
    )
}