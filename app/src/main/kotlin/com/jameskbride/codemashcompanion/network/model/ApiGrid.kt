package com.jameskbride.codemashcompanion.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiGrid constructor(@SerializedName("rooms") var rooms:List<ApiRoom> = listOf())
    : Serializable