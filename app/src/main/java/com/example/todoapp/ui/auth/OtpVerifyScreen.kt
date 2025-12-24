package com.example.todoapp.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpVerifyScreen(
    navController: NavController,
    email: String,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var otp by remember { mutableStateOf("") }
    var otpError by remember { mutableStateOf(false) }

    var remainingTime by remember { mutableStateOf(30) }
    var canResend by remember { mutableStateOf(false) }
//    var viewModel.isLoading by remember { mutableStateOf(false) }

    /* ---------------- Timer ---------------- */
    LaunchedEffect(Unit) {
        startTimer(
            onTick = { remainingTime = it },
            onFinish = { canResend = true }
        )
    }

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
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Verify OTP",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Enter the OTP sent to $email",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(Modifier.height(32.dp))

            /* ---------------- OTP Input ---------------- */
            AppTextField(
                value = otp,
                label = "OTP",
                isError = otpError,
                onValueChange = {
                    if (it.length <= 6) {
                        otp = it
                        otpError = false
                    }
                }
            )

            Spacer(Modifier.height(24.dp))

            /* ---------------- Verify Button ---------------- */
            AppButton(
                text = if (viewModel.isLoading) "Verifying..." else "Verify OTP",
                enabled = !viewModel.isLoading,
                onClick = {
                    otpError = otp.length != 6

                    if (otpError) {
                        Toast.makeText(
                            context,
                            "OTP must be 6 digits",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@AppButton
                    }

                    viewModel.verifyOtp(
                        otp = otp,
                        onSuccess = {
                            Toast.makeText(
                                context,
                                "Verified successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(Routes.TODO_LIST) {
                                popUpTo(Routes.SIGNUP) { inclusive = true }
                            }
                        },
                        onError = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )

            Spacer(Modifier.height(16.dp))

            /* ---------------- Resend OTP ---------------- */
            TextButton(
                onClick = {
//                    scope.launch {
//                        viewModel.isLoading = true
////                        authRepository.resendOtp(email)
//
//                        Toast.makeText(
//                            context,
//                            "OTP resent",
//                            Toast.LENGTH_SHORT
//                        ).show()
//
//                        canResend = false
//                        startTimer(
//                            onTick = { remainingTime = it },
//                            onFinish = { canResend = true }
//                        )
//                        viewModel.isLoading = false
//                    }
                },
                enabled = canResend && !viewModel.isLoading
            ) {
                Text(
                    if (canResend)
                        "Resend OTP"
                    else
                        "Resend OTP in ${remainingTime}s"
                )
            }
        }
    }
}

/* ---------------- Timer Helper ---------------- */
private suspend fun startTimer(
    duration: Int = 30,
    onTick: (Int) -> Unit,
    onFinish: () -> Unit
) {
    for (time in duration downTo 1) {
        onTick(time)
        delay(1000)
    }
    onFinish()
}
