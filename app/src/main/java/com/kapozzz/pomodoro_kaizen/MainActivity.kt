package com.kapozzz.pomodoro_kaizen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kapozzz.common.navigation.Features
import com.kapozzz.common.navigation.LocalAppNavigator
import com.kapozzz.common.navigation.AppNavigatorImpl
import com.kapozzz.pomodoro_kaizen.navigation.AppNavGraph
import com.kapozzz.pomodoro_kaizen.navigation.NavigationProvider
import com.kapozzz.presentation.root_components.AppRootScaffold
import com.kapozzz.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navProvider: NavigationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppTheme {
                CompositionLocalProvider(
                    LocalAppNavigator provides AppNavigatorImpl(navController)
                ) {
                    AppRootScaffold {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .then(it)
                        ) {
                            AppNavGraph(
                                navController = navController,
                                navigationProvider = navProvider,
                                startDestination = Features.Tasks.NESTED_ROUTE
                            )
                        }
                    }
                }
            }
        }
    }
}

