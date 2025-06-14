package com.example.appadvisor.data.repository

import android.util.Log
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.model.request.ForgotPasswordRequest
import com.example.appadvisor.data.model.request.LoginRequest
import com.example.appadvisor.data.model.request.ResetPasswordRequest
import com.example.appadvisor.data.model.request.SignUpRequest
import com.example.appadvisor.data.model.request.VerifyOtpRequest
import com.example.appadvisor.data.model.response.LoginResponse
import com.example.appadvisor.data.model.response.ApiResponse
import com.example.appadvisor.data.network.AuthApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager
) {

    suspend fun signUp(signUpRequest: SignUpRequest): Result<ApiResponse> {
        return try {
            val response = authApiService.signUp(signUpRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Registration failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return try {
            val response = authApiService.login(loginRequest)
            if (response.isSuccessful) {

                response.body()?.let { body ->
                    if (body.token != null && body.role != null && body.name != null) {
                        tokenManager.saveAuthInfo(
                            token = body.token,
                            role = body.role,
                            name = body.name,
                            id = body.id!!
                        )

                        Log.d("TOKEN","${tokenManager.getToken()}")
                        Log.d("ROLE","${tokenManager.getRole()}")
                        Log.d("NAME","${tokenManager.getName()}")
                        Log.d("ID","${tokenManager.getId()}")
                    }
                }

                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Registration failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendOtp(email: String): Result<ApiResponse> {
        return try {
            val response = authApiService.sendOtp(ForgotPasswordRequest(email))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyOtp(email: String, otp: String): Result<ApiResponse> {
        return try {
            val response = authApiService.verifyOtp(VerifyOtpRequest(email, otp))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(email: String, otp: String, newPassword: String): Result<ApiResponse> {
        val request = ResetPasswordRequest(email, otp, newPassword)
        return try {
            val response = authApiService.resetPassword(request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}