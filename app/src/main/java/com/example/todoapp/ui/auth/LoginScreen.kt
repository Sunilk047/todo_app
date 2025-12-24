package com.example.todoapp.ui.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.data.repository.AuthRepository
import com.example.todoapp.ui.components.AppButton
import com.example.todoapp.ui.components.AppTextField
import com.example.todoapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

//    var isLoading by remember { mutableStateOf(false) }

    Scaffold { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Welcome back",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(32.dp))

                /* ---------------- Email ---------------- */
                AppTextField(
                    value = email,
                    label = "Email",
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null)
                    },
                    isError = emailError,
                    onValueChange = {
                        email = it.trim()
                        emailError = false
                    }
                )

                /* ---------------- Password ---------------- */
                AppTextField(
                    value = password,
                    label = "Password",
                    isPassword = true,
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    },
                    isError = passwordError,
                    onValueChange = {
                        password = it
                        passwordError = false
                    }
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(
                        onClick = { navController.navigate(Routes.FORGOT) }
                    ) {
                        Text("Forgot password?")
                    }
                }

                Spacer(Modifier.height(24.dp))

                /* ---------------- Login Button ---------------- */
                AppButton(
                    text = if (viewModel.isLoading) "Please wait..." else "Login",
                    enabled = !viewModel.isLoading,
                    onClick = {
                        emailError = email.isBlank() ||
                                !Patterns.EMAIL_ADDRESS.matcher(email).matches()

                        passwordError = password.isBlank() || password.length < 6

                        if (emailError || passwordError) {
                            Toast.makeText(
                                context,
                                "Please enter valid credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@AppButton
                        }

                        viewModel.login(
                            email,
                            password,
                            onSuccess = {
                                navController.navigate(Routes.TODO_LIST) {
                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                }
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                )

                Spacer(Modifier.height(16.dp))

                TextButton(
                    onClick = { navController.navigate(Routes.SIGNUP) }
                ) {
                    Text("Create new account")
                }
            }
        }
    }
}
