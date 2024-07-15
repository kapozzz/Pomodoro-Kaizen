package com.kapozzz.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kapozzz.ui.AppTheme

val LocalAppDialog =
    staticCompositionLocalOf<AppDialogState> { error("app dialog not provided") }

data class AppDialogState(
    val isVisible: MutableState<Boolean>,
    val message: MutableState<String>,
    val onAccess: MutableState<() -> Unit>,
    val onDismiss: MutableState<() -> Unit>
)

@Composable
fun AppDialog(
    state: AppDialogState
) {
    if (state.isVisible.value) Dialog(
        onDismissRequest = {
            state.onDismiss.value.invoke()
            state.isVisible.value = false
        }
    ) {
        val scrollState = rememberScrollState()
        Card(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .width(300.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AppTheme.colors.container),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.container
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 68.dp, max = 320.dp)
                        .padding(16.dp)
                        .verticalScroll(scrollState),
                    text = state.message.value,
                    style = AppTheme.typo.smallBody,
                    color = AppTheme.colors.onContainer,
                    textAlign = TextAlign.Start
                )
                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        state.onAccess.value.invoke()
                        state.isVisible.value = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {
                        state.onDismiss.value.invoke()
                        state.isVisible.value = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

