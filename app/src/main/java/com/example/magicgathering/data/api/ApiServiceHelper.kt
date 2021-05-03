package com.example.magicgathering.data.api

class ApiServiceHelper(private val magicService: MagicService) {

    suspend fun getCards() = magicService.getCards()
    suspend fun getCardById(id: String) = magicService.getCardById(id)
}