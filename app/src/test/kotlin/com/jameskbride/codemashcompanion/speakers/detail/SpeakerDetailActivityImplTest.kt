package com.jameskbride.codemashcompanion.speakers.detail

import android.content.Intent
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl.Companion.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.utils.test.buildDefaultFullSpeakers
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SpeakerDetailActivityImplTest {
    @Mock private lateinit var qtn: SpeakerDetailActivity
    @Mock private lateinit var supportFragmentManager: androidx.fragment.app.FragmentManager
    @Mock private lateinit var speakerPager: androidx.viewpager.widget.ViewPager
    @Mock private lateinit var speakerPagerAdapter:SpeakerPagerAdapter
    @Mock private lateinit var speakerPagerAdapterFactory:SpeakerPagerAdapterFactory
    @Mock private lateinit var intent:Intent
    @Mock private lateinit var actionBar:ActionBar
    @Mock private lateinit var toolbar:Toolbar
    private lateinit var subject: SpeakerDetailActivityImpl

    private lateinit var speakers:Array<FullSpeaker>
    private lateinit var speakerDetailParams: SpeakersParams

    @Before
    fun setUp() {
        initMocks(this)
        speakers = buildDefaultFullSpeakers()
        speakerDetailParams = SpeakersParams(speakers, 0)
        intent = mock()

        subject = SpeakerDetailActivityImpl(speakerPagerAdapterFactory)

        whenever(qtn.intent).thenReturn(intent)
        whenever(intent.getSerializableExtra(PARAMETER_BLOCK)).thenReturn(speakerDetailParams)
        whenever(speakerPagerAdapterFactory.make(supportFragmentManager)).thenReturn(speakerPagerAdapter)
        whenever(qtn.findViewById<androidx.viewpager.widget.ViewPager>(R.id.speaker_pager)).thenReturn(speakerPager)
        whenever(qtn.findViewById<Toolbar>(R.id.toolbar)).thenReturn(toolbar)
        whenever(qtn.supportActionBar).thenReturn(actionBar)
        whenever(qtn.supportFragmentManager).thenReturn(supportFragmentManager)
    }

    @Test
    fun onCreateSetsTheContentView() {
        subject.onCreate(null, qtn)

        verify(qtn).setContentView(R.layout.activity_speaker_detail)
    }

    @Test
    fun onCreateSetsTheTitle() {
        subject.onCreate(null, qtn)

        verify(toolbar).setTitle(R.string.speaker_detail)
    }

    @Test
    fun onCreateConfiguresTheViewPager() {
        subject.onCreate(null, qtn)

        assertSame(speakerPagerAdapter, subject.speakerDetailPagerAdapter)
        verify(speakerPagerAdapter).updateSpeakers(speakers)
        verify(speakerPager).setAdapter(subject.speakerDetailPagerAdapter)
        verify(speakerPager).setCurrentItem(speakerDetailParams.index)
    }

    @Test
    fun onCreateConfiguresTheUpNavigation() {
        whenever(qtn.supportActionBar).thenReturn(actionBar)
        subject.onCreate(null, qtn)

        verify(qtn).setSupportActionBar(toolbar)
        verify(actionBar).setDisplayHomeAsUpEnabled(true)
    }

    @Test
    fun itCanNavigateUpWhenHomeIsSelected() {
        val menuItem = mock<MenuItem>()
        whenever(menuItem.itemId).thenReturn(android.R.id.home)

        val result = subject.onOptionsItemSelected(menuItem, qtn)

        Assert.assertTrue(result)
        verify(qtn).onBackPressed()
    }

    @Test
    fun itNavigatesAccordingToSuperWhenHomeIsNotSelected() {
        val menuItem = mock<MenuItem>()
        @IdRes val someInt = 42
        whenever(menuItem.itemId).thenReturn(someInt)

        subject.onOptionsItemSelected(menuItem, qtn)

        verify(qtn).callSuperOnOptionsItemSelected(menuItem)
    }
}