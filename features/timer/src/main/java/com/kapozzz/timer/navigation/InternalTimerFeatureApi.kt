package com.kapozzz.timer.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kapozzz.common.navigation.api.FeatureApi
import com.kapozzz.common.navigation.Features
import com.kapozzz.timer.presentation.TimerScreen
import com.kapozzz.timer.presentation.TimerViewModel

internal object InternalTimerFeatureApi : FeatureApi {
    override fun registerGraph(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = Features.Timer.SCREEN_ROUTE,
            route = Features.Timer.NESTED_ROUTE
        ) {
            composable(route = Features.Timer.SCREEN_ROUTE) {

                val viewModel: TimerViewModel = hiltViewModel()

                TimerScreen(
                    state = viewModel.currentState,
                    sendEvent = viewModel::setEvent,
                    effect = viewModel.effect
                )
            }
        }
    }
}