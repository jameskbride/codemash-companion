package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.network.Session
import java.util.*

data class SessionData(val sessions: LinkedHashMap<Date, Array<Session>> = linkedMapOf())