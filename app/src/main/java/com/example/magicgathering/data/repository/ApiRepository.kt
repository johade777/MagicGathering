package com.example.magicgathering.data.repository

import com.example.magicgathering.data.api.ApiServiceHelper

class ApiRepository (private val apiHelper: ApiServiceHelper) {

    suspend fun getCards() = apiHelper.getCards()
    suspend fun getCard(id: String) = apiHelper.getCardById(id)
}