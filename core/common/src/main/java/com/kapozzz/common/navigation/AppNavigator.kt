package com.kapozzz.common.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions

val LocalAppNavigator = staticCompositionLocalOf<AppNavigator> { error("Navigator not provided") }

interface AppNavigator {
    fun back()
    fun navigateToAddTask(id: String? = null)
    fun navigateToTimer(id: String? = null)
}

class AppNavigatorImpl(
    private val navController: NavController
) : AppNavigator {
    override fun back() {
        navController.popBackStack()
    }

    override fun navigateToAddTask(id: String?) {
        val path = Features.Tasks.createTaskRouteWithId(id)
        navController.navigate(route = path)
    }

    override fun navigateToTimer(id: String?) {
        val path = Features.Timer.createTimerRouteWithId(id)
        navController.navigate(path)
    }
}