package com.kapozzz.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.kapozzz.ui.AppTheme

@Composable
fun AppButton(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    image: (@Composable () -> Unit)? = null
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .background(AppTheme.colors.primary)
    ) {
        image?.let {
            image()
        }
        Text(
            modifier = Modifier
                .then(
                    image?.let {
                        Modifier.padding(start = 8.dp, end = 16.dp)
                    } ?: Modifier
                ),
            text = name,
            style = AppTheme.typo.mediumBody,
            color = AppTheme.colors.onPrimary
        )
    }
}