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

    @Before
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun itCanReturnSpeakerCount() {
        val speakers = buildSpeakers()

        subject.setSpeakers(speakers)

        assertEquals(speakers.size, subject.getCount())
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