package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivity
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl.Companion.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailParams
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SpeakersFragmentImplTest {

    private lateinit var layoutInflater: LayoutInflater
    private lateinit var viewGroup: ViewGroup
    private lateinit var speakersFragmentPresenter:SpeakersFragmentPresenter
    private lateinit var speakersViewAdapter:SpeakersRecyclerViewAdapter
    private lateinit var speakersViewAdapterFactory:SpeakersViewAdapterFactory
    private lateinit var speakersFragment:SpeakersFragment
    private lateinit var view:View
    private lateinit var speakersView:RecyclerView
    private lateinit var context:Context
    private lateinit var gridLayoutManager:GridLayoutManager
    private lateinit var intentFactory:IntentFactory
    private lateinit var activity:AppCompatActivity

    private lateinit var subject: SpeakersFragmentImpl

    @Before
    fun setUp() {
        layoutInflater = mock()
        viewGroup = mock()
        speakersFragmentPresenter = mock()
        speakersViewAdapter = mock()
        speakersViewAdapterFactory = mock()
        speakersFragment = mock()
        view = mock()
        speakersView = mock()
        context = mock()
        gridLayoutManager = mock()
        intentFactory = mock()
        activity = mock()
        subject = SpeakersFragmentImpl(speakersFragmentPresenter, speakersViewAdapterFactory, intentFactory)

        whenever(speakersFragment.getView()).thenReturn(view)
        whenever(speakersFragment.getContext()).thenReturn(context)
        whenever(speakersFragment.activity).thenReturn(activity)
        whenever(view.findViewById<RecyclerView>(R.id.speakers)).thenReturn(speakersView)
        whenever(speakersViewAdapterFactory.make(speakersFragmentPresenter)).thenReturn(speakersViewAdapter)
        whenever(layoutInflater.inflate(R.layout.fragment_speakers, viewGroup, false)).thenReturn(view)
    }

    @Test
    fun itInflatesTheViewOnCreateView() {
        val inflatedView = subject.onCreateView(layoutInflater, viewGroup, null, speakersFragment)

        assertSame(view, inflatedView)
        verify(layoutInflater).inflate(R.layout.fragment_speakers, viewGroup, false)
    }

    @Test
    fun itSetsTheViewOnThePresenterOnCreate() {
        subject.onCreateView(layoutInflater, viewGroup, null, speakersFragment)
    }

    @Test
    fun itSetsTheSpeakersViewAdapterOnCreate() {
        subject.onCreateView(layoutInflater, viewGroup, null, speakersFragment)

        verify(speakersView).setAdapter(speakersViewAdapter)
    }

    @Test
    fun itConfiguresTheViewForASmoothScrollingGridview() {
        whenever(speakersFragment.makeGridLayoutManager(2)).thenReturn(gridLayoutManager)

        subject.onCreateView(layoutInflater, viewGroup, null, speakersFragment)

        verify(speakersView).setLayoutManager(gridLayoutManager)
        verify(speakersView).setDrawingCacheEnabled(true)
        verify(speakersView).setItemViewCacheSize(20)
        verify(speakersView).setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
    }

    @Test
    fun itRequestsTheSpeakerDataOnResume() {
        subject.onResume()

        verify(speakersFragmentPresenter).requestSpeakerData()
    }

    @Test
    fun itSetsTheSpeakersOnSpeakerDataReceived() {
        subject.onCreateView(layoutInflater, viewGroup, null, speakersFragment)
        val speakers = buildDefaultSpeakers()

        subject.onSpeakerDataRetrieved(speakers)

        verify(speakersViewAdapter).setSpeakers(speakers)
    }
    
    @Test
    fun itNavigatesToTheDetailsView() {
        val intent = mock<Intent>()
        val speakers = buildDefaultSpeakers()
        whenever(intentFactory.make(context, SpeakerDetailActivity::class.java)).thenReturn(intent)
        subject.onCreateView(layoutInflater, viewGroup, null, speakersFragment)

        subject.navigateToDetails(speakers, 0)

        val extraCaptor = argumentCaptor<SpeakerDetailParams>()

        verify(intent).putExtra(eq(PARAMETER_BLOCK), extraCaptor.capture())
        val speakerDetailParams = extraCaptor.firstValue
        assertArrayEquals(speakers, speakerDetailParams.speakers)
        assertEquals(0, speakerDetailParams.index)
    }
}