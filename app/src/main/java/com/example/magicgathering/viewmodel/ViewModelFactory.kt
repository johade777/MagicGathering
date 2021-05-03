package com.example.magicgathering.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.magicgathering.data.api.ApiServiceHelper
import com.example.magicgathering.data.repository.ApiRepository

class ViewModelFactory(private val apiHelper: ApiServiceHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardListViewModel::class.java)) {
            return CardListViewModel(ApiRepository(apiHelper)) as T
        }else if (modelClass.isAssignableFrom(CardDetailsViewModel::class.java)) {
            return CardDetailsViewModel(ApiRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}