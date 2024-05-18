package com.kapozzz.timer.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.kapozzz.common.navigation.api.FeatureApi

interface TimerApi: FeatureApi

class TimerApiImpl: TimerApi {
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        InternalTimerFeatureApi.registerGraph(navController, navGraphBuilder)
    }
}