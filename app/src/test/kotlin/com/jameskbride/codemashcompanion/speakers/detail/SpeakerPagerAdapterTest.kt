package com.jameskbride.codemashcompanion.speakers.detail

import androidx.fragment.app.FragmentManager
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.utils.test.buildDefaultFullSpeakers
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SpeakerPagerAdapterTest {

    private lateinit var fragmentManager: androidx.fragment.app.FragmentManager
    private lateinit var speakers:Array<FullSpeaker>
    private lateinit var subject:SpeakerPagerAdapter
    private lateinit var speakerPagerAdapterImpl:SpeakerPagerAdapterImpl

    @Before
    fun setUp() {
        fragmentManager = mock()
        speakerPagerAdapterImpl = mock()
        speakers = buildDefaultFullSpeakers(2)
        subject = SpeakerPagerAdapter(fragmentManager, speakerPagerAdapterImpl)
    }

    @Test
    fun itCanReturnTheCountOfSpeakers() {
        whenever(speakerPagerAdapterImpl.getCount()).thenReturn(2)

        assertEquals(2, subject.count)
    }

    @Test
    fun itNotifiesOfUpdates() {
        val speakerPagerAdapter = mock<SpeakerPagerAdapter>()
        val subject = SpeakerPagerAdapterImpl()
        subject.speakerPagerAdapter = speakerPagerAdapter

        subject.updateSpeakers(speakers)

        verify(speakerPagerAdapter).notifyDataSetChanged()
    }
}