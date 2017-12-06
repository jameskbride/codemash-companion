package com.jameskbride.codemashcompanion.speakers

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks

class SpeakersFragmentPresenterTest {

    @Mock
    private lateinit var conferenceRepository:ConferenceRepository

    @Mock
    private lateinit var eventBus:EventBus

    @Mock
    private lateinit var view:SpeakersFragmentView

    private lateinit var subject:SpeakersFragmentPresenter

    private lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        initMocks(this)
        testScheduler = TestScheduler()
        subject = SpeakersFragmentPresenter(eventBus, conferenceRepository, testScheduler, testScheduler)
        subject.view = view
    }

    @Test
    fun whenSpeakerDataIsReceivedThenTheDataIsPassedToTheView() {
        val speakers = buildDefaultSpeakers()

        `when`(conferenceRepository.getSpeakers()).thenReturn(Maybe.just(speakers))

        subject.requestSpeakerData()

        testScheduler.triggerActions()

        verify(view).onSpeakerDataRetrieved(speakers)
    }

    @Test
    fun itDelegatesToTheViewToNavigateToDetails() {
        val speakers = buildDefaultSpeakers()

        subject.navigateToDetails(speakers, 0)

        verify(view).navigateToDetails(speakers, 0)
    }
}