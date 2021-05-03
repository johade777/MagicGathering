package com.example.magicgathering.data.model

import com.google.gson.annotations.SerializedName

data class GetCardByIdResponse(
    @SerializedName("card") val card: Card)