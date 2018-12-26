package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import java.text.SimpleDateFormat
import java.util.*

class SessionData(val sessions: Array<FullSession> = arrayOf()) {

    fun groupSessionsByDate(): List<SessionsByDate> {
        return sessions.groupBy { session ->
            val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)
            val calendar = Calendar.getInstance()
            calendar.time = dateFormatter.parse(session?.SessionStartTime)
            val shortDateFormatter = SimpleDateFormat(Session.SHORT_DATE_FORMAT)
            shortDateFormatter.format(calendar.time)
        }.map { entry -> SessionsByDate(entry.key, entry.value) }
    }
}

