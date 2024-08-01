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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kapozzz.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBackClick: () -> Unit,
    onBackClickEnabled: Boolean,
    actions: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        modifier = Modifier.background(AppTheme.colors.primary),
        title = {
            Text(
                text = title,
                style = AppTheme.typo.mediumTitle,
                color = AppTheme.colors.onPrimary
            )
        },
        navigationIcon = {
            if (onBackClickEnabled) IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = AppTheme.colors.onPrimary
                )
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.primary,
            actionIconContentColor = AppTheme.colors.onPrimary
        )
    )
}

data class AppTopBarState(
    val title: MutableState<String>,
    val onBackClick: MutableState<() -> Unit>,
    val onBackClickEnabled: MutableState<Boolean>,
    val enabled: MutableState<Boolean>,
    val actions: MutableState<@Composable RowScope.() -> Unit>
)

