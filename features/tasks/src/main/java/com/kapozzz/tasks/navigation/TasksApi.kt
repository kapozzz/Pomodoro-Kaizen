package com.kapozzz.tasks.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.kapozzz.common.navigation.api.FeatureApi
import com.kapozzz.domain.models.Task


interface TasksApi: FeatureApi

class TasksApiImpl: TasksApi {
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        TODO("Not yet implemented")
    }
}
