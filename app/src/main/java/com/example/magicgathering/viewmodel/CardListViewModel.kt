package com.example.magicgathering.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.magicgathering.data.repository.ApiRepository
import com.example.magicgathering.util.Resource
import kotlinx.coroutines.Dispatchers

class CardListViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getCards() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val cards = apiRepository.getCards()
            emit(Resource.success(data = cards))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}