package com.jameskbride.codemashcompanion.bus

import com.jameskbride.codemashcompanion.network.Session

data class SessionsReceivedEvent constructor(val sessions: Array<Session> = arrayOf())