package com.talhakasikci.finn360fe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.talhakasikci.finn360fe.models.watchlist.WatchList
import com.talhakasikci.finn360fe.repository.WatchListRepository
import kotlinx.coroutines.launch

class WatchListViewModel(application: Application): AndroidViewModel(application)
{
    private val repository = WatchListRepository(application.applicationContext)

    private val _WLresults = MutableLiveData<ArrayList<WatchList>?>()
    val WLresults: LiveData<ArrayList<WatchList>?> get() = _WLresults

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getUserWatchList(sortBy: String, direction: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getUserWatchList(sortBy, direction)
                if (response.isSuccessful) {
                    _WLresults.value = response.body()
                } else {
                    val errorBody =response.errorBody()?.string()
                    _errorMessage.value = "Hata: ${response.code()} - $errorBody"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Bağlantı hatası: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}












