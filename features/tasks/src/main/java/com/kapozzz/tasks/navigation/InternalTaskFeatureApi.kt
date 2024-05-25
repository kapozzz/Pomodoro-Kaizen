package com.kapozzz.tasks.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.kapozzz.common.navigation.Features
import com.kapozzz.tasks.screens.create_task_screen.CreateTaskScreen
import com.kapozzz.tasks.screens.create_task_screen.CreateTaskViewModel

class InternalTaskFeatureApi: TasksApi {
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.navigation(startDestination = Features.Tasks.CREATE_TASK_ROUTE, route = Features.Tasks.NESTED_ROUTE) {
            composable(route = Features.Tasks.CREATE_TASK_ROUTE) {
                val viewModel: CreateTaskViewModel = hiltViewModel()
                CreateTaskScreen(
                    sendEvent = viewModel::setEvent,
                    state = viewModel.currentState,
                    effects = viewModel.effect
                )
            }
        }
    }
}