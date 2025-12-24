package com.example.todoapp.ui.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
//import com.example.todoapp.data.repository.AuthRepository
import com.example.todoapp.ui.components.AppButton
import com.example.todoapp.ui.components.AppTextField
import com.example.todoapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(16.dp))

                Text("Create Account", style = MaterialTheme.typography.headlineMedium)
                Text(
                    "Sign up to get started",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(32.dp))

                AppTextField(
                    value = name,
                    label = "Full Name",
                    isError = nameError,
                    onValueChange = {
                        name = it
                        nameError = false
                    }
                )

                AppTextField(
                    value = email,
                    label = "Email",
                    isError = emailError,
                    onValueChange = {
                        email = it.trim()
                        emailError = false
                    }
                )

                AppTextField(
                    value = password,
                    label = "Password",
                    isPassword = true,
                    isError = passwordError,
                    onValueChange = {
                        password = it
                        passwordError = false
                    }
                )

                AppTextField(
                    value = confirmPassword,
                    label = "Confirm Password",
                    isPassword = true,
                    isError = confirmPasswordError,
                    onValueChange = {
                        confirmPassword = it
                        confirmPasswordError = false
                    }
                )

                Spacer(Modifier.height(24.dp))

                AppButton(
                    text = if (viewModel.isLoading) "Please wait..." else "Sign Up",
                    enabled = !viewModel.isLoading,
                    onClick = {
                        nameError = name.isBlank()
                        emailError = email.isBlank() ||
                                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        passwordError = password.length < 6
                        confirmPasswordError = confirmPassword != password

                        if (nameError || emailError || passwordError || confirmPasswordError) {
                            Toast.makeText(
                                context,
                                "Please fill all fields correctly",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@AppButton
                        }

                        viewModel.signup(
                            email = email,
                            password = password,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Account created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate(Routes.DASHBOARD) {
                                    popUpTo(Routes.SIGNUP) { inclusive = true }
                                }
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        )
                    })

                Spacer(Modifier.height(16.dp))

                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Already have an account? Login")
                }
            }
        }
    }
}

