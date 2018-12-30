package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.xwray.groupie.Group

open class SessionsRecyclerViewAdapterImpl(
        open val sessionsFragmentPresenter: SessionsFragmentPresenter,
        open val layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory(),
        open val sessionToListItemConverter: SessionToListItemConverter = SessionToListItemConverter()) {

    fun setSessions(sessionData: SessionData, qtn: SessionsRecyclerViewAdapter) {
        val sessionsByDate = sessionData.groupSessionsByDate()
        val sessionsList = populateSessionList(sessionsByDate)
        qtn.addAll(sessionsList)
    }

    private fun populateSessionList(sessionsByDate: List<SessionsByDate>): List<Group> {
        return sessionToListItemConverter.populateSessionList(sessionsByDate) { session: FullSession ->
            sessionsFragmentPresenter.navigateToSessionDetail(session)
        }
    }
}