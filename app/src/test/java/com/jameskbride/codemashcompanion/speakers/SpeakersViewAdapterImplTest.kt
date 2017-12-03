package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.widget.ImageView
import com.jameskbride.codemashcompanion.network.Speaker
import com.jameskbride.codemashcompanion.utils.PicassoWrapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

class SpeakersViewAdapterImplTest {

    @Mock
    private lateinit var speakerViewAdapter: SpeakersViewAdapter

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var picassoWrapper: PicassoWrapper

    private lateinit var subject: SpeakersViewAdapterImpl

    private val speakers: Array<Speaker> = buildSpeakers()

    @Before
    fun setUp() {
        initMocks(this)

        subject = SpeakersViewAdapterImpl(context, picassoWrapper)

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

    @Test
    fun itCreatesANewImageViewWhenTheOldViewIsNull() {
        val newImageView = mock(ImageView::class.java)
        val speakers = buildSpeakers()
        `when`(speakerViewAdapter.buildImageView()).thenReturn(newImageView)
        `when`(picassoWrapper.with(context)).thenReturn(picassoWrapper)
        `when`(picassoWrapper.load(speakers[0].GravatarUrl)).thenReturn(picassoWrapper)

        val imageView = subject.getView(0, null, null, speakerViewAdapter)

        assertSame(newImageView, imageView)

        verify(speakerViewAdapter).buildImageView()
        verify(picassoWrapper).with(context)
        verify(picassoWrapper).load(speakers[0].GravatarUrl)
        verify(picassoWrapper).into(newImageView)
    }

    @Test
    fun itReusesTheImageViewWhenTheOldViewIsNotNull() {
        val newImageView = mock(ImageView::class.java)
        val speakers = buildSpeakers()
        `when`(picassoWrapper.with(context)).thenReturn(picassoWrapper)
        `when`(picassoWrapper.load(speakers[0].GravatarUrl)).thenReturn(picassoWrapper)

        val imageView = subject.getView(0, newImageView, null, speakerViewAdapter)

        assertSame(newImageView, imageView)

        verify(speakerViewAdapter, times(0)).buildImageView()
        verify(picassoWrapper).with(context)
        verify(picassoWrapper).load(speakers[0].GravatarUrl)
        verify(picassoWrapper).into(newImageView)
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