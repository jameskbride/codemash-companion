package com.jameskbride.codemashcompanion.speakers

import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.jameskbride.codemashcompanion.R
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks

class SpeakersFragmentImplTest {

    @Mock
    private lateinit var layoutInflater: LayoutInflater

    @Mock
    private lateinit var viewGroup: ViewGroup

    @Mock
    private lateinit var speakersFragmentPresenter:SpeakersFragmentPresenter

    @Mock
    private lateinit var speakersViewAdapter:SpeakersViewAdapter

    @Mock
    private lateinit var speakersViewAdapterFactory:SpeakersViewAdapterFactory

    @Mock
    private lateinit var speakersFragment:SpeakersFragment

    @Mock
    private lateinit var view:View

    @Mock
    private lateinit var speakersGridView:GridView

    @Mock
    private lateinit var context:FragmentActivity

    private lateinit var subject: SpeakersFragmentImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = SpeakersFragmentImpl(speakersFragmentPresenter, speakersViewAdapterFactory)

        `when`(speakersFragment.getView()).thenReturn(view)
        `when`(speakersFragment.getActivity()).thenReturn(context)
        `when`(view.findViewById<GridView>(R.id.speakers)).thenReturn(speakersGridView)
        `when`(speakersViewAdapterFactory.make(context)).thenReturn(speakersViewAdapter)
    }

    @Test
    fun itInflatesTheViewOnCreateView() {
        subject.onCreateView(layoutInflater, viewGroup, null, speakersFragment)

        verify(layoutInflater).inflate(R.layout.fragment_speakers, viewGroup, false)
    }

    @Test
    fun itSetsTheViewOnThePresenterOnCreate() {
        subject.onCreateView(layoutInflater, viewGroup, null, speakersFragment)
    }

    @Test
    fun itSetsTheSpeakersViewAdapterOnCreate() {
        subject.onCreateView(layoutInflater, viewGroup, null, speakersFragment)

        verify(speakersGridView).setAdapter(speakersViewAdapter)
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