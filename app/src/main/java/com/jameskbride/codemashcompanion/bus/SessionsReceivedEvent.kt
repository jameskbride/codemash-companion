package com.jameskbride.codemashcompanion.bus

import com.jameskbride.codemashcompanion.network.model.ApiSession

data class SessionsReceivedEvent constructor(val sessions: List<ApiSession> = listOf())