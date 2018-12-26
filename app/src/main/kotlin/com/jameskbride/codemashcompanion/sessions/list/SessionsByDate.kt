package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import java.text.SimpleDateFormat
import java.util.*

class SessionsByDate constructor(val sessionDate: String, val sessions: List<FullSession> = listOf()) {

    fun sessionsByTime(): Pair<String, Map<Date, List<FullSession>>> {
        val sessions = sessions.groupBy { session ->
            val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)
            dateFormatter.parse(session?.SessionStartTime)
        }

        return Pair<String, Map<Date, List<FullSession>>>(sessionDate, sessions)
    }
}