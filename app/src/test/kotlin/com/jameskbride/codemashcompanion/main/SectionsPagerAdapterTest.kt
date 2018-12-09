package com.jameskbride.codemashcompanion.main

import androidx.fragment.app.FragmentManager
import com.jameskbride.codemashcompanion.sessions.SessionsFragment
import com.jameskbride.codemashcompanion.speakers.SpeakersFragment
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class SectionsPagerAdapterTest {
    private lateinit var fragmentManager: androidx.fragment.app.FragmentManager

    private lateinit var subject:SectionsPagerAdapter

    @Before
    fun setUp() {
        fragmentManager = mock(androidx.fragment.app.FragmentManager::class.java)

        subject = SectionsPagerAdapter(fragmentManager)
    }

    @Test
    fun itCanReturnTheSpeakersFragment() {
        assertTrue(subject.getItem(1) is SpeakersFragment)
    }

    @Test
    fun itCanReturnTheSessionsFragment() {
        assertTrue(subject.getItem(0) is SessionsFragment)
    }
}