package com.kapozzz.tasks.screens.list_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kapozzz.ui.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PagePicker(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val currentTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }
    TabRow(
        modifier = modifier,
        selectedTabIndex = currentTabIndex.value,
        contentColor = AppTheme.colors.outline,
        indicator = { tabPositions ->
            if (currentTabIndex.value < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[currentTabIndex.value]),
                    color = AppTheme.colors.primary
                )
            }
        }
    ) {
        ListScreenTabs.entries.forEachIndexed { index, listScreenTabs ->
            Tab(
                selected = currentTabIndex.value == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = listScreenTabs.name,
                        style = AppTheme.typo.mediumDefaultTittle,
                        color = AppTheme.colors.onBackground
                    )
                },
            )
        }
    }
}

enum class ListScreenTabs(name: String) {
    Actual("Actual"),
    Completed("Completed")
}
