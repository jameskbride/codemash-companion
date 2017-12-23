package com.jameskbride.codemashcompanion.schedule

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class BookmarkedSessionsRetrieverTest {

    @Mock private lateinit var conferenceRepository:ConferenceRepository

    private lateinit var subject:BookmarkedSessionsRetriever

    @Before
    fun setUp() {
        initMocks(this)

        subject = BookmarkedSessionsRetriever(conferenceRepository)
    }

    @Test
    fun itCanRetrieveBookmarkedSessions() {
        val expectedResponse = Maybe.just(arrayOf(FullSession()))

        whenever(conferenceRepository.getBookmarkedSessions()).thenReturn(expectedResponse)

        val actualResponse = subject.getSessions()

        assertSame(expectedResponse, actualResponse)
        verify(conferenceRepository).getBookmarkedSessions()
    }
}