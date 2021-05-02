package com.example.magicgathering.data.api

import com.example.magicgathering.data.model.GetCardResponse
import retrofit2.http.GET

interface MagicService {

    @GET("cards")
    suspend fun getCards(): GetCardResponse
}