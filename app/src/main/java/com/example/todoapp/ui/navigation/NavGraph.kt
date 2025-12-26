package com.example.todoapp.ui.navigation

import androidx.compose.animation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.runtime.Composable
import com.example.todoapp.ui.auth.*
import com.example.todoapp.ui.profile.ProfileScreen
import com.example.todoapp.ui.splash.SplashScreen
import com.example.todoapp.ui.todo.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,

        enterTransition = {
            slideInHorizontally { it } + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally { -it / 3 } + fadeOut()
        },
        popEnterTransition = {
            slideInHorizontally { -it } + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally { it } + fadeOut()
        }
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.SIGNUP) {
            SignupScreen(navController)
        }

        composable(Routes.FORGOT) {
            ForgotPasswordScreen(navController)
        }

        composable(Routes.RESET) {
            SetNewPasswordScreen(navController)
        }
        composable(Routes.PROFILE) {
            ProfileScreen(navController)
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(navController)
        }

        composable(
            route = "${Routes.ADD_EDIT}?todoId={todoId}",
            arguments = listOf(
                navArgument("todoId") {
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId")
            AddEditToDoScreen(
                navController = navController,
                todoId = todoId
            )
        }
    }
}
