package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    /* ---------------- UI STATE ---------------- */
    var isLoading by mutableStateOf(false)
        private set

    /* ---------------- LOGIN ---------------- */
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        isLoading = true
        authRepository.login(email, password) { success, error ->
            isLoading = false
            if (success) {
                onSuccess()
            } else {
                onError(error ?: "Login failed")
            }
        }
    }

    /* ---------------- SIGNUP ---------------- */
    fun signup(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        isLoading = true
        authRepository.signup(email, password) { success, error ->
            isLoading = false
            if (success) {
                onSuccess()
            } else {
                onError(error ?: "Signup failed")
            }
        }
    }

    /* ---------------- RESET PASSWORD ---------------- */
    fun resetPassword(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        isLoading = true
        authRepository.resetPassword(email) { success, error ->
            isLoading = false
            if (success) {
                onSuccess()
            } else {
                onError(error ?: "Failed to send reset email")
            }
        }
    }

    /* ---------------- SESSION ---------------- */
    fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }

    fun logout() {
        authRepository.logout()
    }

    /* ---------------- OTP (UI PLACEHOLDER) ---------------- */
    /**
     * Firebase Email/Password auth does NOT use OTP.
     * This is kept only because your UI has an OTP screen.
     */
    fun verifyOtp(
        otp: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (otp.length == 6) {
            onSuccess()
        } else {
            onError("Invalid OTP")
        }
    }
    // AuthViewModel.kt

    val userEmail: String?
        get() = authRepository.getUserEmail()

        val userName: String
        get() = authRepository.getUserName()
//    var userName by mutableStateOf("")
//        private set
//
//    var userPhoto by mutableStateOf<String?>(null)
//        private set
//
//    fun updateUser(name: String, photo: String?) {
//        userName = name
//        userPhoto = photo
//    }
}
