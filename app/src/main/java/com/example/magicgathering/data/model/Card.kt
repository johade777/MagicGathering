package com.example.magicgathering.data.model

import com.google.gson.annotations.SerializedName

data class Card(
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String
            )