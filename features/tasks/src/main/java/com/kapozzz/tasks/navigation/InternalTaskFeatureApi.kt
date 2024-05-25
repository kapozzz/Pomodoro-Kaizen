package com.kapozzz.tasks.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.kapozzz.common.navigation.Features

class InternalTaskFeatureApi: TasksApi {
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.navigation(startDestination = Features.Tasks.TASKS_ROUTE, route = Features.Tasks.NESTED_ROUTE) {
            // TODO
        }
    }
}