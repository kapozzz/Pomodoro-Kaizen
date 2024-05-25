package com.kapozzz.pomodoro_kaizen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kapozzz.pomodoro_kaizen.navigation.NavigationProvider

@Composable
fun AppNavGraph(
    navController: NavHostController,
    navigationProvider: NavigationProvider,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        navigationProvider.timer.registerGraph(
            navController, this
        )

        navigationProvider.tasks.registerGraph(
            navController, this
        )

    }
}