package com.jameskbride.codemashcompanion.speakers.detail

import android.content.Intent
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl.Companion.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class SpeakerDetailActivityImplTest {
    private lateinit var activity: SpeakerDetailActivity
    private lateinit var supportFragmentManager: FragmentManager
    private lateinit var speakerPager: ViewPager
    private lateinit var speakerPagerAdapter:SpeakerPagerAdapter
    private lateinit var speakerPagerAdapterFactory:SpeakerPagerAdapterFactory

    private lateinit var subject: SpeakerDetailActivityImpl

    private lateinit var speakers:Array<Speaker>
    private lateinit var speakerDetailParams:SpeakerDetailParams
    private lateinit var intent:Intent

    @Before
    fun setUp() {
        activity = mock()
        supportFragmentManager = mock()
        speakerPager = mock()
        speakerPagerAdapter = mock()
        speakerPagerAdapterFactory = mock()

        speakers = buildDefaultSpeakers()
        speakerDetailParams = SpeakerDetailParams(speakers, 0)
        intent = mock()

        subject = SpeakerDetailActivityImpl(speakerPagerAdapterFactory)

        whenever(activity.intent).thenReturn(intent)
        whenever(intent.getSerializableExtra(PARAMETER_BLOCK)).thenReturn(speakerDetailParams)
        whenever(speakerPagerAdapterFactory.make(supportFragmentManager, speakers)).thenReturn(speakerPagerAdapter)
        whenever(activity.findViewById<ViewPager>(R.id.speaker_pager)).thenReturn(speakerPager)
        whenever(activity.supportFragmentManager).thenReturn(supportFragmentManager)
    }

    @Test
    fun onCreateSetsTheContentView() {
        subject.onCreate(null, activity)

        verify(activity).setContentView(R.layout.activity_speaker_detail)
    }

    @Test
    fun onCreateConfiguresTheViewPager() {
        subject.onCreate(null, activity)

        assertSame(speakerPagerAdapter, subject.speakerDetailPagerAdapter)
        verify(speakerPager).setAdapter(subject.speakerDetailPagerAdapter)
        verify(speakerPager).setCurrentItem(speakerDetailParams.index)
    }
}