package com.example.magicgathering.data.model

import com.google.gson.annotations.SerializedName

data class GetCardsResponse(
    @SerializedName("cards") val cardList: List<Card>)