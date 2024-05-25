package com.kapozzz.presentation.root_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kapozzz.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBackClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        modifier = Modifier.background(AppTheme.colors.container),
        title = {
            Text(
                text = title,
                style = AppTheme.typo.mediumTitle,
                color = AppTheme.colors.onContainer
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = actions
    )
}

data class AppTopBarState(
    val title: MutableState<String>,
    val onBackClick: MutableState<() -> Unit>,
    val enabled: MutableState<Boolean>,
    val actions: MutableState<@Composable RowScope.() -> Unit>
)

//@Composable
//@Preview
//private fun TopBarPreview() {
//    AppTheme {
//        AppTopBar(title = "New task", onBackClick = { /*TODO*/ })
//    }
//}