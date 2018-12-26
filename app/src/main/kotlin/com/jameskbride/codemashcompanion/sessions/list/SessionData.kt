package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import java.text.SimpleDateFormat
import java.util.*

class SessionData(val sessions: Array<FullSession> = arrayOf()) {

    fun groupSessionsByDate(): List<SessionsByDate> {
        return sessions
                .groupBy { session -> toSessionDate(session) }
                .map { entry -> SessionsByDate(entry.key, entry.value) }
                .sortedWith(compareBy { sessionsByDate -> sessionsByDate.sessionDate })
    }

    private fun toSessionDate(session: FullSession): String {
        val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)
        val calendar = Calendar.getInstance()
        calendar.time = dateFormatter.parse(session?.SessionStartTime)
        val shortDateFormatter = SimpleDateFormat(Session.SHORT_DATE_FORMAT)
        return shortDateFormatter.format(calendar.time)
    }
}

