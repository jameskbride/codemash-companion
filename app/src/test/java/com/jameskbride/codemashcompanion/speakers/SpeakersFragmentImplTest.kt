package com.jameskbride.codemashcompanion.speakers

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks

class SpeakersFragmentImplTest {

    @Mock
    private lateinit var layoutInflater: LayoutInflater

    @Mock
    private lateinit var viewGroup: ViewGroup

    @Mock
    private lateinit var speakersFragmentPresenter:SpeakersFragmentPresenter

    @InjectMocks
    private lateinit var subject: SpeakersFragmentImpl

    @Before
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun itInflatesTheView() {
        subject.onCreateView(layoutInflater, viewGroup, null)

        verify(layoutInflater).inflate(R.layout.fragment_speakers, viewGroup, false)
    }

    @Test
    fun itOpensThePresenterOnResume() {
        subject.onResume()

        verify(speakersFragmentPresenter).open()
    }

    @Test
    fun itRequestsTheSpeakerDataOnResume() {
        subject.onResume()

        verify(speakersFragmentPresenter).requestSpeakerData()
    }

    @Test
    fun itClosesThePresenterOnPause() {
        subject.onPause()

        verify(speakersFragmentPresenter).close()
    }
}