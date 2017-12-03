package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.jameskbride.codemashcompanion.utils.LogWrapper
import com.jameskbride.codemashcompanion.utils.PicassoWrapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

class SpeakersRecyclerViewAdapterTest {

    @Mock
    private lateinit var speakerImage:ImageView

    @Mock
    private lateinit var speakerFirstName:TextView

    @Mock
    private lateinit var speakerLastName:TextView

    @Mock
    private lateinit var logWrapper:LogWrapper

    @Mock
    private lateinit var picassoWrapper:PicassoWrapper

    @Mock
    private lateinit var view:View

    @Mock
    private lateinit var viewGroup:ViewGroup

    @Mock
    private lateinit var layoutInflaterFactory:LayoutInflaterFactory

    @Mock
    private lateinit var context:Context

    @Before
    fun setUp() {
        initMocks(this)

        `when`(view.findViewById<ImageView>(R.id.speaker_image)).thenReturn(speakerImage)
        `when`(view.findViewById<TextView>(R.id.speaker_first_name)).thenReturn(speakerFirstName)
        `when`(view.findViewById<TextView>(R.id.speaker_last_name)).thenReturn(speakerLastName)
        `when`(speakerImage.context).thenReturn(context)
    }

    @Test
    fun itCanBindTheViewHolder() {
        val speakerViewHolder = mock(SpeakerViewHolder::class.java)
        val qtn = mock(SpeakersRecyclerViewAdapter::class.java)
        val subject = SpeakersRecyclerViewAdapterImpl(layoutInflaterFactory)
        val speakers = buildSpeakers()
        subject.setSpeakers(speakers, qtn)

        subject.onBindViewHolder(speakerViewHolder, 0)

        verify(speakerViewHolder).bind(speakers[0])
        verify(qtn).notifyDataSetChanged()
    }

    @Test
    fun itCanCreateTheViewHolder() {
        val subject = SpeakersRecyclerViewAdapterImpl(layoutInflaterFactory)
        `when`(viewGroup.context).thenReturn(context)
        `when`(layoutInflaterFactory.inflate(context, R.layout.view_speaker, viewGroup)).thenReturn(view)

        subject.onCreateViewHolder(viewGroup, 0)

        verify(layoutInflaterFactory).inflate(context, R.layout.view_speaker, viewGroup)
    }

    @Test
    fun itCanGetItemCount() {
        val speakers = buildSpeakers()
        val qtn = mock(SpeakersRecyclerViewAdapter::class.java)
        val subject = SpeakersRecyclerViewAdapterImpl(layoutInflaterFactory)
        subject.setSpeakers(speakers, qtn)

        assertEquals(speakers.size, subject.getItemCount())
    }

    @Test
    fun speakerViewHolderHoldsTheSpeakerViews() {
        val subject = SpeakerViewHolder(view, logWrapper = logWrapper, picassoWrapper = picassoWrapper)

        assertSame(speakerImage, subject.speakerImage)
        assertSame(speakerFirstName, subject.speakerFirstName)
        assertSame(speakerLastName, subject.speakerLastName)
    }

    @Test
    fun bindLoadsTheSpeakerDataIntoTheView() {
        val speaker = buildSpeaker()
        `when`(picassoWrapper.with(context)).thenReturn(picassoWrapper)
        `when`(picassoWrapper.load("${speaker.GravatarUrl}?s=180&d=mm")).thenReturn(picassoWrapper)
        `when`(picassoWrapper.placeholder(R.drawable.ic_person_black)).thenReturn(picassoWrapper)
        `when`(picassoWrapper.resize(500, 500)).thenReturn(picassoWrapper)
        `when`(picassoWrapper.centerCrop()).thenReturn(picassoWrapper)

        val subject = SpeakerViewHolder(view, logWrapper = logWrapper, picassoWrapper = picassoWrapper)

        subject.bind(speaker)

        verify(speakerFirstName).setText(speaker.FirstName)
        verify(speakerLastName).setText(speaker.LastName)
        verify(picassoWrapper).into(speakerImage)

    }

    private fun buildSpeakers():Array<Speaker> {
        return arrayOf(buildSpeaker())
    }

    private fun buildSpeaker(): Speaker {
        return Speaker(
                LinkedInProfile = "linkedin",
                Id = "1234",
                LastName = "Smith",
                TwitterLink = "twitter",
                GitHubLink = "github",
                FirstName = "John",
                GravatarUrl = "gravitar",
                Biography = "biography",
                BlogUrl = "blog"
        )
    }
}

