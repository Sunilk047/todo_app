package com.example.todoapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.widget.Toast
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.ui.theme.bluePrimary
import com.example.todoapp.ui.theme.blueSecondary
import com.example.todoapp.ui.theme.white
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.TodoViewModel

@Composable
fun AppDrawer(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
    todoViewModel: TodoViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {

        /* ================= HEADER ================= */

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(blueSecondary)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

//                if (viewModel.userPhoto != null) {
//                    AsyncImage(
//                        model = viewModel.userPhoto,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(72.dp)
//                            .clip(CircleShape)
//                    )
//                } else {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
//                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = viewModel.userName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = viewModel.userEmail ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        /* ================= MENU ================= */

        Column(modifier = Modifier.padding(vertical = 8.dp)) {

            NavigationDrawerItem(
                label = { Text("Dashboard") },
                icon = { Icon(Icons.Default.List, null) },
                selected = false,
                onClick = {
                    navController.navigate(Routes.DASHBOARD)
                }
            )

            NavigationDrawerItem(
                label = { Text("Profile") },
                icon = { Icon(Icons.Default.Person, null) },
                selected = false,
                onClick = {
                    navController.navigate(Routes.PROFILE)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            /* ================= LOGOUT ================= */

            NavigationDrawerItem(
                label = { Text("Logout") },
                icon = { Icon(Icons.Default.ExitToApp, null) },
                selected = false,
                onClick = {
                    todoViewModel.clearOnLogout()
                    viewModel.logout()
                    Toast.makeText(
                        context,
                        "Logged out successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    navController.navigate(Routes.LOGIN) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun AsyncImage(model: String?, contentDescription: Nothing?, modifier: Modifier) {
    TODO("Not yet implemented")
}
