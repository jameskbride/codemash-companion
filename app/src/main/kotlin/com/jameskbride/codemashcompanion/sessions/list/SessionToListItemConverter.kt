package com.jameskbride.codemashcompanion.sessions.list

import android.view.View
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.listitems.DateHeaderListItem
import com.jameskbride.codemashcompanion.sessions.list.listitems.EmptyListItem
import com.jameskbride.codemashcompanion.sessions.list.listitems.SessionListItem
import com.jameskbride.codemashcompanion.sessions.list.listitems.TimeHeaderListItem
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class SessionToListItemConverter {

    fun populateSessionList(sessionsByDate: List<SessionsByDate>, sessionOnClick: (FullSession) -> Unit): List<Group> {
        var dateGroups = sessionsByDate.map{ sessionsByDate ->
            var timeSections = sessionsByDate.sessionsByTime().map { sessionsByTime ->
                ExpandableGroup(TimeHeaderListItem(sessionsByTime.sessionTime), isTimeExpanded(sessionsByTime.sessionTime)).apply {
                    addAll(sessionsByTime.sessions.map { session -> SessionListItem(session, View.OnClickListener { sessionOnClick(session) }) })
                }
            }

            ExpandableGroup(DateHeaderListItem(sessionsByDate.sessionDate), isDateExpanded(sessionsByDate.sessionDate)).apply {
                addAll(timeSections)
            }
        }

        return if (dateGroups.isEmpty()) { listOf(EmptyListItem()) } else { dateGroups }
    }

    private fun isTimeExpanded(sessionTime: Date): Boolean {
        val sessionTimeInstant = Instant.ofEpochMilli(sessionTime.getTime())
                .atZone(ZoneId.systemDefault()).toInstant()
        val sessionDateTime = LocalDateTime.ofInstant(sessionTimeInstant, ZoneId.systemDefault())

        val currentDateTime = LocalDateTime.now()

        return sessionDateTime.isEqual(currentDateTime) || sessionDateTime.isAfter(currentDateTime)
    }

    private fun isDateExpanded(sessionsDate: String): Boolean {
        val currentDate = LocalDate.now()

        val sessionDate = LocalDate.parse(sessionsDate, DateTimeFormatter.ISO_DATE)

        return sessionDate.isEqual(currentDate) || sessionDate.isAfter(currentDate)
    }
}