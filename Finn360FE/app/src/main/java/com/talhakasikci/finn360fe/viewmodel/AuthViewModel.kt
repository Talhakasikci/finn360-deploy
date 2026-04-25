package com.talhakasikci.finn360fe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talhakasikci.finn360fe.models.Auth.AuthResponse
import com.talhakasikci.finn360fe.models.Auth.LoginRequest
import com.talhakasikci.finn360fe.models.Auth.RegisterRequest
import com.talhakasikci.finn360fe.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository(application.applicationContext)

    private val _loginResult = MutableLiveData<AuthResponse?>()
    val loginResult: LiveData<AuthResponse?> get() = _loginResult

    private val _registerResult = MutableLiveData<AuthResponse>()
    val registerResult: LiveData<AuthResponse?> get() = _registerResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun login(request: LoginRequest) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.loginUser(request)

                if (response.isSuccessful) {
                    _loginResult.value = response.body()
                } else {
                    // Backend hata döndü (401, 404, 500 vs.)
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = "Hata: ${response.code()} - $errorBody"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Bağlantı hatası: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(request: RegisterRequest) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.registerUser(request)

                if(response.isSuccessful){
                    _registerResult.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = "Hata: ${response.code()} - $errorBody"
                }

            }catch (e: Exception) {
                _errorMessage.value = "Bağlantı hatası: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}