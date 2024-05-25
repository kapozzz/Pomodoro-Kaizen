package com.kapozzz.presentation.root_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalTopBarState = staticCompositionLocalOf<AppTopBarState> { error("top bar not provided") }

object AppUiComponent {

    val topBarState: AppTopBarState
        @Composable
        get() = LocalTopBarState.current


}


@Composable
fun AppRootScaffold(
    content: @Composable (Modifier) -> Unit
) {
    val topBarState = remember {
        AppTopBarState(
            title = mutableStateOf("Top Bar Name"),
            onBackClick = mutableStateOf({}),
            enabled = mutableStateOf(false)
        )
    }

    CompositionLocalProvider(
        LocalTopBarState provides topBarState,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (topBarState.enabled.value) {
                    AppTopBar(
                        title = topBarState.title.value,
                        onBackClick = topBarState.onBackClick.value,
                    )
                }
            }
        ) {
            content(Modifier.padding(it))
        }
    }
}

