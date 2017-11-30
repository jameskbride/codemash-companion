package com.jameskbride.codemashcompanion.speakers

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import org.greenrobot.eventbus.EventBus
import org.junit.Before
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SpeakersFragmentPresenterTest {

    @Mock
    private lateinit var conferenceRepository:ConferenceRepository

    @Mock
    private lateinit var eventBus:EventBus

    @InjectMocks
    private lateinit var subject:SpeakersFragmentPresenter

    @Before
    fun setUp() {
        initMocks(this)
    }
}