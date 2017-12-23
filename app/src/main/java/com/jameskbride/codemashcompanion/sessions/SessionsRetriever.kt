package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import io.reactivex.Maybe

interface SessionsRetriever {
    fun getSessions(): Maybe<Array<FullSession>>
}

class AllSessionRetriever(val conferenceRepository: ConferenceRepository) : SessionsRetriever {
    override fun getSessions(): Maybe<Array<FullSession>> {
        return conferenceRepository.getSessions()
    }
}