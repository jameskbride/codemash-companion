package com.jameskbride.codemashcompanion.speakers

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.network.Speaker
import io.reactivex.Observable
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
        val speakers = buildSpeakers()

        `when`(conferenceRepository.getSpeakers()).thenReturn(Observable.fromArray(speakers))

        subject.requestSpeakerData()

        testScheduler.triggerActions()

        verify(view).onSpeakerDataRetrieved(speakers)
    }

    private fun buildSpeakers(): Array<Speaker> {
        return arrayOf(Speaker(
                LinkedInProfile = "linkedin",
                Id = "1234",
                LastName = "Smith",
                TwitterLink = "twitter",
                GitHubLink = "github",
                FirstName = "John",
                GravatarUrl = "gravitar",
                Biography = "biography",
                BlogUrl = "blog"
        ))
    }
}