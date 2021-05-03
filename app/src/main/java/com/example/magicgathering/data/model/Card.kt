package com.example.magicgathering.data.model

import com.google.gson.annotations.SerializedName

data class Card(
            @SerializedName("id") val id: String,
            @SerializedName("name") val name: String,
            @SerializedName("imageUrl") val imageUrl: String?,
            @SerializedName("type") val type: String,
            @SerializedName("manaCost") val manaCost: String,
            @SerializedName("set") val set: String,
            @SerializedName("text") val text: String
            )