package com.jameskbride.codemashcompanion.main

import android.support.v4.app.FragmentManager
import com.jameskbride.codemashcompanion.speakers.SpeakersFragment
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class SectionsPagerAdapterTest {
    private lateinit var fragmentManager:FragmentManager

    private lateinit var subject:SectionsPagerAdapter

    @Before
    fun setUp() {
        fragmentManager = mock(FragmentManager::class.java)

        subject = SectionsPagerAdapter(fragmentManager)
    }

    @Test
    fun itCanReturnTheSpeakersFragment() {
        val result = subject.getItem(1)
        assertTrue(result is SpeakersFragment)
    }
}