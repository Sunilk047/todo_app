package com.example.todoapp.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.ui.components.AppButton
import com.example.todoapp.ui.components.AppTextField

@Composable
fun SetNewPasswordScreen(
    navController: NavController
) {

    val context = LocalContext.current

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.VisibilityOff,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Set New Password",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Enter your new password",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(Modifier.height(32.dp))

            /* ---------------- New Password ---------------- */
            AppTextField(
                value = password,
                label = "New Password",
                isPassword = true,
                isError = passwordError,
                onValueChange = {
                    password = it
                    passwordError = false
                }
            )

            Spacer(Modifier.height(12.dp))

            /* ---------------- Confirm Password ---------------- */
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

            /* ---------------- Set Password Button ---------------- */
            AppButton(
                text = if (isLoading) "Please wait..." else "Set Password",
                enabled = !isLoading,
                onClick = {
                    passwordError = password.length < 6
                    confirmPasswordError = confirmPassword != password

                    if (passwordError || confirmPasswordError) {
                        Toast.makeText(
                            context,
                            "Please enter valid password",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@AppButton
                    }

                    isLoading = true

                    // 👉 Call reset password API here
                    // authRepository.setNewPassword(password)

                    isLoading = false

                    Toast.makeText(
                        context,
                        "Password updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = { navController.popBackStack() }
            ) {
                Text("Back")
            }
        }
    }
}
