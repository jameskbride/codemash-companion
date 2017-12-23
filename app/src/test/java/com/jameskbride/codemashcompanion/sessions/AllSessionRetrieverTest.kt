package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AllSessionRetrieverTest {
    @Mock private lateinit var conferenceRepository: ConferenceRepository

    private lateinit var subject: AllSessionRetriever

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        subject = AllSessionRetriever(conferenceRepository)
    }

    @Test
    fun itCanRetrieveAllSessions() {
        val expectedResponse = Maybe.just(arrayOf(FullSession()))

        whenever(conferenceRepository.getSessions()).thenReturn(expectedResponse)

        val actualResponse = subject.getSessions()

        assertSame(expectedResponse, actualResponse)
        verify(conferenceRepository).getSessions()
    }
}