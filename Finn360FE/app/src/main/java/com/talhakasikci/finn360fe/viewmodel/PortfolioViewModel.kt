package com.talhakasikci.finn360fe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.talhakasikci.finn360fe.models.PortfolioRequest
import com.talhakasikci.finn360fe.models.PortfolioResponse
import com.talhakasikci.finn360fe.repository.PortfolioRepository
import com.talhakasikci.finn360fe.repository.WatchListRepository
import kotlinx.coroutines.launch

class PortfolioViewModel(application: Application): AndroidViewModel(application) {

    private val repository = PortfolioRepository(application.applicationContext)

    val portfolioData = MutableLiveData<PortfolioResponse?>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val operationStatus = MutableLiveData<String>()

    fun fetchPortfolio() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getUserPortfolio()
                if (response.isSuccessful && response.body() != null) {
                    portfolioData.value = response.body()
                } else {
                    errorMessage.value = "Hata: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Bağlantı Hatası: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun addToPortfolio(symbol: String, assetType: String, quantity: Double, price: Double, instrumentId: String? = null) {
        isLoading.value = true
        val request = PortfolioRequest(
            symbol = symbol,
            assetType = assetType,
            description = symbol, // İstersen detaylı isim alabilirsin
            quantity = quantity,
            averageBuyPrice = price,
            instrumentId = instrumentId
        )

        viewModelScope.launch {
            try {
                val response = repository.addInvestment(request)
                if (response.isSuccessful) {
                    operationStatus.value = "Başarıyla eklendi!"
                    fetchPortfolio() // Listeyi yenile
                } else {
                    errorMessage.value = "Ekleme başarısız"
                }
            } catch (e: Exception) {
                errorMessage.value = "Hata: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun deleteAsset(symbol: String) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.deleteInvestment(symbol)
                if (response.isSuccessful) {
                    operationStatus.value = "Silindi: $symbol"
                    fetchPortfolio() // Listeyi yenile
                } else {
                    errorMessage.value = "Silme hatası"
                }
            } catch (e: Exception) {
                errorMessage.value = "Hata: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}
