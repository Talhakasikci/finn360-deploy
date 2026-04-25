package com.talhakasikci.finn360fe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talhakasikci.finn360fe.models.Portfolio

class ViewPagerViewModel: ViewModel() {

    private val _totalAmount = MutableLiveData<String>()
    private val _portfolioArraylist = MutableLiveData<ArrayList<Portfolio>>()
    val totalAmount: LiveData<String> = _totalAmount
    val portfolioArraylist: LiveData<ArrayList<Portfolio>> = _portfolioArraylist

    init {
        _totalAmount.value = "0"
        _portfolioArraylist.value = ArrayList()
    }

    fun updatePortfolioAmount(newTotal: String, newPortfolioList: ArrayList<Portfolio>) {
        _totalAmount.value = newTotal
        _portfolioArraylist.value = newPortfolioList
    }
}