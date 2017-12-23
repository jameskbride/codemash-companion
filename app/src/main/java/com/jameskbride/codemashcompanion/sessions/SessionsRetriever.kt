package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import io.reactivex.Maybe
import javax.inject.Inject

interface SessionsRetriever {
    fun getSessions(): Maybe<Array<FullSession>>
}

class AllSessionRetriever @Inject constructor(val conferenceRepository: ConferenceRepository) : SessionsRetriever {
    override fun getSessions(): Maybe<Array<FullSession>> {
        return conferenceRepository.getSessions()
    }
}