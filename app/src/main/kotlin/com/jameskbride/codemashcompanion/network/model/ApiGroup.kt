package com.jameskbride.codemashcompanion.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiGroup constructor(
        @SerializedName("sessions") var sessions:List<ApiSession> = listOf()
) : Serializable