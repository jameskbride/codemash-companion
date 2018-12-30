package com.jameskbride.codemashcompanion.sessions.list

import android.view.View
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.listitems.DateHeaderListItem
import com.jameskbride.codemashcompanion.sessions.list.listitems.EmptyListItem
import com.jameskbride.codemashcompanion.sessions.list.listitems.SessionListItem
import com.jameskbride.codemashcompanion.sessions.list.listitems.TimeHeaderListItem
import com.xwray.groupie.Group

class SessionToListItemConverter {

    fun populateSessionList(sessionsByDate: List<SessionsByDate>, sessionOnClick: (FullSession) -> Unit): List<Group> {
        val sessionsList = mutableListOf<Group>()

        sessionsByDate.forEach{ sessionsByDate ->
            sessionsList.add(DateHeaderListItem(sessionsByDate.sessionDate))
            sessionsByDate.sessionsByTime().forEach { sessionsByTime ->
                sessionsList.add(TimeHeaderListItem(sessionsByTime.sessionTime))
                sessionsByTime.sessions.forEach { session ->
                    sessionsList.add(SessionListItem(session, View.OnClickListener {
                        sessionOnClick(session)
                    }))
                }
            }
        }

        if (sessionsList.isEmpty()) {
            sessionsList.add(EmptyListItem())
        }

        return sessionsList
    }
}