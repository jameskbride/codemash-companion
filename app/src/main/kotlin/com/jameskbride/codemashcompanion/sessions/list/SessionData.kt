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
                .sortedWith(compareBy { sessionsByDate -> toCalendar(sessionsByDate.sessionDate, Session.SHORT_DATE_FORMAT) })
    }

    private fun toSessionDate(session: FullSession): String {
        val calendar = toCalendar(session.SessionStartTime, Session.TIMESTAMP_FORMAT)
        val shortDateFormatter = SimpleDateFormat(Session.SHORT_DATE_FORMAT)
        return shortDateFormatter.format(calendar?.time)
    }

    private fun toCalendar(sessionStartTime: String?, dateFormat: String): Calendar? {
        val dateFormatter = SimpleDateFormat(dateFormat)
        val calendar = Calendar.getInstance()
        calendar.time = dateFormatter.parse(sessionStartTime)
        return calendar
    }
}

