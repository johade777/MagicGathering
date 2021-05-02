package com.example.magicgathering.data.model

import com.google.gson.annotations.SerializedName

data class GetCardResponse(
    @SerializedName("cards") val cardList: List<Card>)