package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import org.junit.Assert.assertSame
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
    private lateinit var speakersViewAdapter:SpeakersRecyclerViewAdapter

    @Mock
    private lateinit var speakersViewAdapterFactory:SpeakersViewAdapterFactory

    @Mock
    private lateinit var speakersFragment:SpeakersFragment

    @Mock
    private lateinit var view:View

    @Mock
    private lateinit var speakersView:RecyclerView

    @Mock
    private lateinit var context:Context

    @Mock
    private lateinit var gridLayoutManager:GridLayoutManager

    private lateinit var subject: SpeakersFragmentImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = SpeakersFragmentImpl(speakersFragmentPresenter, speakersViewAdapterFactory)

        `when`(speakersFragment.getView()).thenReturn(view)
        `when`(speakersFragment.getContext()).thenReturn(context)
        `when`(view.findViewById<RecyclerView>(R.id.speakers)).thenReturn(speakersView)
        `when`(speakersViewAdapterFactory.make()).thenReturn(speakersViewAdapter)
        `when`(layoutInflater.inflate(R.layout.fragment_speakers, viewGroup, false)).thenReturn(view)
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
        `when`(speakersFragment.makeGridLayoutManager(2)).thenReturn(gridLayoutManager)

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
        val speakers = buildSpeakers()

        subject.onSpeakerDataRetrieved(speakers)

        verify(speakersViewAdapter).setSpeakers(speakers)
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