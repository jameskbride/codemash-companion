package com.jameskbride.codemashcompanion.sessions.list

import android.view.View
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.listitems.DateHeaderListItem
import com.jameskbride.codemashcompanion.sessions.list.listitems.EmptyListItem
import com.jameskbride.codemashcompanion.sessions.list.listitems.SessionListItem
import com.jameskbride.codemashcompanion.sessions.list.listitems.TimeHeaderListItem
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.Section

class SessionToListItemConverter {

    fun populateSessionList(sessionsByDate: List<SessionsByDate>, sessionOnClick: (FullSession) -> Unit): List<Group> {
        var dateGroups = sessionsByDate.map{ sessionsByDate ->
            var timeSections = sessionsByDate.sessionsByTime().map { sessionsByTime ->
                var timeSection = Section()
                timeSection.setHeader(TimeHeaderListItem(sessionsByTime.sessionTime))
                timeSection.addAll(sessionsByTime.sessions.map { session -> SessionListItem(session, View.OnClickListener { sessionOnClick(session) }) })
                timeSection
            }

            ExpandableGroup(DateHeaderListItem(sessionsByDate.sessionDate), true).apply {
                addAll(timeSections)
            }
        }

        return if (dateGroups.isEmpty()) { listOf(EmptyListItem()) } else { dateGroups }
    }
}