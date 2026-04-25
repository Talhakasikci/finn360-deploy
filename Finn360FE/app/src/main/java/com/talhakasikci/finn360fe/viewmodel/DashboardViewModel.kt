package com.talhakasikci.finn360fe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.talhakasikci.finn360fe.models.DashboardResponse
import com.talhakasikci.finn360fe.repository.AuthRepository
import com.talhakasikci.finn360fe.repository.DashboardRepository
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application): AndroidViewModel(application) {
    private val repository = DashboardRepository(application.applicationContext)

    private val _dashboardResult = MutableLiveData<DashboardResponse?>()
    val dashboardResult: LiveData<DashboardResponse?> get() = _dashboardResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getDashboardData() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getDashboard()

                if (response.isSuccessful) {
                    _dashboardResult.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = "Hata: ${response.code()} - $errorBody"
                }
            }catch (e: Exception) {
                _errorMessage.value = "Bağlantı hatası: ${e.localizedMessage}"
            }finally {
                _isLoading.value = false
            }
        }
    }
}