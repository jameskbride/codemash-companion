package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivity
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl.Companion.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.speakers.detail.SpeakersParams
import com.jameskbride.codemashcompanion.speakers.list.SpeakersRecyclerViewAdapter
import com.jameskbride.codemashcompanion.speakers.list.SpeakersViewAdapterFactory
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.Toaster
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SpeakersFragmentImplTest {

    @Mock private lateinit var layoutInflater: LayoutInflater
    @Mock private lateinit var viewGroup: ViewGroup
    @Mock private lateinit var presenter:SpeakersFragmentPresenter
    @Mock private lateinit var speakersViewAdapter: SpeakersRecyclerViewAdapter
    @Mock private lateinit var speakersViewAdapterFactory: SpeakersViewAdapterFactory
    @Mock private lateinit var qtn:SpeakersFragment
    @Mock private lateinit var view:View
    @Mock private lateinit var speakersView:RecyclerView
    @Mock private lateinit var speakersRefresh:SwipeRefreshLayout
    @Mock private lateinit var context:Context
    @Mock private lateinit var gridLayoutManager:GridLayoutManager
    @Mock private lateinit var intentFactory:IntentFactory
    @Mock private lateinit var activity:AppCompatActivity
    @Mock private lateinit var toaster:Toaster

    private lateinit var subject: SpeakersFragmentImpl

    @Before
    fun setUp() {
        initMocks(this)
        subject = SpeakersFragmentImpl(presenter, speakersViewAdapterFactory, intentFactory, toaster)

        whenever(qtn.getView()).thenReturn(view)
        whenever(qtn.getContext()).thenReturn(context)
        whenever(qtn.activity).thenReturn(activity)
        whenever(view.findViewById<RecyclerView>(R.id.speakers)).thenReturn(speakersView)
        whenever(view.findViewById<SwipeRefreshLayout>(R.id.speakers_refresh)).thenReturn(speakersRefresh)
        whenever(speakersViewAdapterFactory.make(presenter)).thenReturn(speakersViewAdapter)
        whenever(layoutInflater.inflate(R.layout.fragment_speakers, viewGroup, false)).thenReturn(view)
    }

    @Test
    fun itInflatesTheViewOnCreateView() {
        val inflatedView = subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        assertSame(view, inflatedView)
        verify(layoutInflater).inflate(R.layout.fragment_speakers, viewGroup, false)
    }

    @Test
    fun itSetsTheViewOnThePresenterOnCreate() {
        subject.onCreateView(layoutInflater, viewGroup, null, qtn)
    }

    @Test
    fun itSetsTheSpeakersViewAdapterOnCreate() {
        subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        verify(speakersView).setAdapter(speakersViewAdapter)
    }

    @Test
    fun itSetsTheRefreshListenerOnCreate() {
        subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        val refreshListenerCaptor = argumentCaptor<SwipeRefreshLayout.OnRefreshListener>()
        verify(speakersRefresh).setOnRefreshListener(refreshListenerCaptor.capture())

        refreshListenerCaptor.firstValue.onRefresh()
        verify(presenter).refreshConferenceData()
        verify(speakersRefresh).setRefreshing(true)
    }

    @Test
    fun itConfiguresTheViewForASmoothScrollingGridview() {
        whenever(qtn.makeGridLayoutManager(2)).thenReturn(gridLayoutManager)

        subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        verify(speakersView).setLayoutManager(gridLayoutManager)
        verify(speakersView).setDrawingCacheEnabled(true)
        verify(speakersView).setItemViewCacheSize(20)
        verify(speakersView).setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
    }

    @Test
    fun itRequestsTheSpeakerDataOnResume() {
        subject.onResume()

        verify(presenter).requestSpeakerData()
    }

    @Test
    fun itOpensThePresenterOnResume() {
        subject.onResume()

        verify(presenter).open()
    }

    @Test
    fun itClosesThePresenterOnPause() {
        subject.onPause()

        verify(presenter).close()
    }

    @Test
    fun itSetsTheSpeakersOnSpeakerDataReceived() {
        subject.onCreateView(layoutInflater, viewGroup, null, qtn)
        val speakers = buildDefaultSpeakers()

        subject.onSpeakerDataRetrieved(speakers)

        verify(speakersViewAdapter).setSpeakers(speakers)
        verify(speakersRefresh).setRefreshing(false)
    }

    @Test
    fun itCanDisplayAnErrorMessage() {
        whenever(toaster.makeText(activity, R.string.could_not_refresh, Toast.LENGTH_SHORT)).thenReturn(toaster)
        subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        subject.displayErrorMessage(R.string.could_not_refresh)

        verify(toaster).show()
    }

    @Test
    fun itCanStopRefreshing() {
        subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        subject.stopRefreshing()

        verify(speakersRefresh).setRefreshing(false)
    }
    
    @Test
    fun itNavigatesToTheDetailsView() {
        val intent = mock<Intent>()
        val speakers = buildDefaultSpeakers()
        whenever(intentFactory.make(context, SpeakerDetailActivity::class.java)).thenReturn(intent)
        subject.onCreateView(layoutInflater, viewGroup, null, qtn)

        subject.navigateToDetails(speakers, 0)

        val extraCaptor = argumentCaptor<SpeakersParams>()

        verify(intent).putExtra(eq(PARAMETER_BLOCK), extraCaptor.capture())
        val speakerDetailParams = extraCaptor.firstValue
        assertArrayEquals(speakers, speakerDetailParams.speakers)
        assertEquals(0, speakerDetailParams.index)
    }
}