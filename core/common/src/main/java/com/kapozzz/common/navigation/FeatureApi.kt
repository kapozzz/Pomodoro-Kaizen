package com.kapozzz.common.navigation.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureApi {
    fun registerGraph(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    )

}