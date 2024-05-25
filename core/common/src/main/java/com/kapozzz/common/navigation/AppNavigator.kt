package com.kapozzz.common.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalAppNavigator = staticCompositionLocalOf<AppNavigator> { error("Navigator not provided") }

interface AppNavigator {
    fun back()
}

class AppNavigatorImpl(
    private val navController: NavController
): AppNavigator {
    override fun back() {
        navController.popBackStack()
    }
}