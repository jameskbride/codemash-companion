package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import java.text.SimpleDateFormat

class SessionsByDate constructor(val sessionDate: String, val sessions: List<FullSession> = listOf()) {

    fun sessionsByTime(): List<SessionsByTime> {
        val sessionsByTime = sessions.groupBy { session ->
            val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)
            dateFormatter.parse(session?.SessionStartTime)
        }
        return sessionsByTime
                .map { entry -> SessionsByTime(entry.key, entry.value)}
                .sortedWith(compareBy { sessionByTime -> sessionByTime.sessionTime })
    }
}