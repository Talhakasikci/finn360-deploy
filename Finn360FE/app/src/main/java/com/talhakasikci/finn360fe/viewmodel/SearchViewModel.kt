package com.talhakasikci.finn360fe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.talhakasikci.finn360fe.models.Search
import com.talhakasikci.finn360fe.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel (application: Application): AndroidViewModel(application) {
    private val repository = SearchRepository(application.applicationContext)

    private val _searchResults = MutableLiveData<ArrayList<Search>?>()
    val searchResults: LiveData<ArrayList<Search>?> get() = _searchResults

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getSearchResults(request: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getSearchResults(request)
                if (response.isSuccessful) {
                    _searchResults.value = response.body()
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