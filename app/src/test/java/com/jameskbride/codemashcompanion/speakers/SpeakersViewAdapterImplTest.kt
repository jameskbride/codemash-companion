package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import com.jameskbride.codemashcompanion.network.Speaker
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SpeakersViewAdapterImplTest {

    @Mock
    private lateinit var context: Context

    @InjectMocks
    private lateinit var subject: SpeakersViewAdapterImpl

    private val speakers: Array<Speaker> = buildSpeakers()

    @Before
    fun setUp() {
        initMocks(this)

        subject.setSpeakers(speakers)
    }

    @Test
    fun itCanReturnSpeakerCount() {
        assertEquals(speakers.size, subject.getCount())
    }

    @Test
    fun itCanGetTheSpeakerByIndex() {
        val johnSmith: Speaker = subject.getItem(0) as Speaker
        assertEquals("Smith", johnSmith.LastName)

        val walterWhite: Speaker = subject.getItem(1) as Speaker
        assertEquals("White", walterWhite.LastName)
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
        ), Speaker(LinkedInProfile = "linkedin",
                Id = "1235",
                LastName = "White",
                TwitterLink = "twitter",
                GitHubLink = "github",
                FirstName = "Walter",
                GravatarUrl = "gravitar",
                Biography = "biography",
                BlogUrl = "blog"
        ))
    }
}