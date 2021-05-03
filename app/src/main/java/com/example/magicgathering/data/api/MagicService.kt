package com.example.magicgathering.data.api

import com.example.magicgathering.data.model.Card
import com.example.magicgathering.data.model.GetCardByIdResponse
import com.example.magicgathering.data.model.GetCardsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MagicService {

    @GET("cards")
    suspend fun getCards(): GetCardsResponse

    @GET("cards/{id}")
    suspend fun getCardById(@Path("id") id: String?): GetCardByIdResponse
}