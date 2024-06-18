package com.kapozzz.tasks.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.kapozzz.common.navigation.Features
import com.kapozzz.tasks.screens.create_task_screen.CreateTaskScreen
import com.kapozzz.tasks.screens.create_task_screen.CreateTaskViewModel
import com.kapozzz.tasks.screens.list_screen.ListScreen
import com.kapozzz.tasks.screens.list_screen.ListScreenViewModel

class InternalTaskFeatureApi : TasksApi {
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.navigation(
            startDestination = Features.Tasks.TASKS_ROUTE,
            route = Features.Tasks.NESTED_ROUTE
        ) {
            composable(
                route = Features.Tasks.CREATE_TASK_ROUTE,
                arguments = listOf(navArgument("id") { defaultValue = "" })
            ) {
                val taskId = it.arguments?.getString("id")
                val viewModel: CreateTaskViewModel = hiltViewModel()
                if (!taskId.isNullOrEmpty()) viewModel.initializeTask(taskId)
                CreateTaskScreen(
                    sendEvent = viewModel::setEvent,
                    state = viewModel.currentState,
                    effects = viewModel.effect
                )
            }
            composable(route = Features.Tasks.TASKS_ROUTE) {
                val viewModel: ListScreenViewModel = hiltViewModel()
                ListScreen(
                    state = viewModel.currentState,
                    sendEvent = viewModel::setEvent,
                    effect = viewModel.effect
                )
            }
        }
    }
}