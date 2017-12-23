package com.jameskbride.codemashcompanion.schedule

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.SessionsRetriever
import io.reactivex.Maybe
import javax.inject.Inject

class BookmarkedSessionsRetriever @Inject constructor(val conferenceRepository: ConferenceRepository) : SessionsRetriever {
    override fun getSessions(): Maybe<Array<FullSession>> {
        return conferenceRepository.getBookmarkedSessions()
    }
}