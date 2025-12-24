package com.example.todoapp.ui.navigation

import SplashScreen
import androidx.compose.animation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import com.example.todoapp.data.repository.AuthRepository
import com.example.todoapp.ui.auth.*
import com.example.todoapp.ui.profile.ProfileScreen
import com.example.todoapp.ui.todo.*
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

///** Hilt EntryPoint to access AuthRepository inside composables */
//@EntryPoint
//@InstallIn(SingletonComponent::class)
//interface AuthRepoEntryPoint {
//    fun authRepository(): AuthRepository
//}
//@OptIn(ExperimentalAnimationApi::class)
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
//        composable(Routes.TODO_LIST) {
//            TodoListScreen(navController)
//        }

//        composable(
//            route = "otpVerify/{email}",
//            arguments = listOf(navArgument("email") {
//                type = NavType.StringType
//            })
//        ) { backStackEntry ->
//            OtpVerifyScreen(
//                navController = navController,
//                email = backStackEntry.arguments?.getString("email")!!
//            )
//        }

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


//        composable(
//            "${Routes.ADD_EDIT}?todoId={todoId}",
//            arguments = listOf(navArgument("todoId") { nullable = true })
//        ) {
//            AddEditToDoScreen(navController)
//        }
    }
}
