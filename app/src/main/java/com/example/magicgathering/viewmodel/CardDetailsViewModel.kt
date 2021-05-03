package com.example.magicgathering.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.magicgathering.data.repository.ApiRepository
import com.example.magicgathering.util.Resource
import kotlinx.coroutines.Dispatchers

class CardDetailsViewModel(private val apiRepository: ApiRepository) : ViewModel()  {

    fun getCardById(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val card = apiRepository.getCard(id)
            emit(Resource.success(data = card))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}