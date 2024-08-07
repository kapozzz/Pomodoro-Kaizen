package com.kapozzz.presentation.root_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapozzz.common.navigation.LocalAppNavigator
import com.kapozzz.common.navigation.AppNavigator
import com.kapozzz.presentation.components.AppDialog
import com.kapozzz.presentation.components.AppDialogState
import com.kapozzz.presentation.components.LocalAppDialog

val LocalTopBarState = staticCompositionLocalOf<AppTopBarState> { error("top bar not provided") }
val LocalFloatingButtonState = staticCompositionLocalOf<AppFloatingButtonState> { error("floating button not provided") }
val LocalSnackBarState = staticCompositionLocalOf<SnackbarHostState> { error("snackbar not provided") }


object AppUiComponents {
    val topBarState: AppTopBarState
        @Composable
        get() = LocalTopBarState.current
    val floatingButtonState: AppFloatingButtonState
        @Composable
        get() = LocalFloatingButtonState.current
    val appNavigator: AppNavigator
        @Composable
        get() = LocalAppNavigator.current
    val snackBarState: SnackbarHostState
        @Composable
        get() = LocalSnackBarState.current
}


@Composable
fun AppRootScaffold(
    content: @Composable (Modifier) -> Unit
) {

    val snackBarState = remember {
        SnackbarHostState()
    }

    val topBarState = remember {
        AppTopBarState(
            title = mutableStateOf("Top Bar Name"),
            onBackClick = mutableStateOf({}),
            onBackClickEnabled = mutableStateOf(false),
            enabled = mutableStateOf(false),
            actions = mutableStateOf(@Composable {

            })
        )
    }

    val floatingButtonState = remember {
        AppFloatingButtonState(
            content = mutableStateOf(@Composable {}),
            enabled = mutableStateOf(false)
        )
    }

    val appDialogState = remember {
        AppDialogState(
            isVisible = mutableStateOf(false),
            message = mutableStateOf(""),
            onAccess = mutableStateOf({}),
            onDismiss = mutableStateOf({})
        )
    }

    CompositionLocalProvider(
        LocalTopBarState provides topBarState,
        LocalFloatingButtonState provides floatingButtonState,
        LocalSnackBarState provides snackBarState,
        LocalAppDialog provides appDialogState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (topBarState.enabled.value) {
                    AppTopBar(
                        title = topBarState.title.value,
                        onBackClick = topBarState.onBackClick.value,
                        onBackClickEnabled = topBarState.onBackClickEnabled.value,
                        actions = topBarState.actions.value
                    )
                }
            },
            floatingActionButton = {
                if (floatingButtonState.enabled.value) {
                    AppFloatingButton(
                        modifier = Modifier.padding(16.dp),
                        content = floatingButtonState.content.value
                    )
                }
            },
            snackbarHost = { SnackbarHost(snackBarState) }
        ) {
            content(Modifier.padding(it))
            AppDialog(state = appDialogState)
        }
    }
}

