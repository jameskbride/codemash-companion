package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.jameskbride.codemashcompanion.utils.LogWrapper
import com.jameskbride.codemashcompanion.utils.PicassoWrapper
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class SpeakersRecyclerViewAdapterTest {

    private lateinit var speakerImage:ImageView
    private lateinit var speakerFirstName:TextView
    private lateinit var speakerLastName:TextView
    private lateinit var logWrapper:LogWrapper
    private lateinit var picassoWrapper:PicassoWrapper
    private lateinit var view:View
    private lateinit var viewGroup:ViewGroup
    private lateinit var layoutInflaterFactory:LayoutInflaterFactory
    private lateinit var context:Context
    private lateinit var speakersFragmentPresenter:SpeakersFragmentPresenter

    @Before
    fun setUp() {
        speakerImage = mock()
        speakerFirstName = mock()
        speakerLastName = mock()
        logWrapper = mock()
        picassoWrapper = mock()
        view = mock()
        viewGroup = mock()
        layoutInflaterFactory = mock()
        context = mock()
        speakersFragmentPresenter = mock()

        whenever(view.findViewById<ImageView>(R.id.speaker_image)).thenReturn(speakerImage)
        whenever(view.findViewById<TextView>(R.id.speaker_first_name)).thenReturn(speakerFirstName)
        whenever(view.findViewById<TextView>(R.id.speaker_last_name)).thenReturn(speakerLastName)
        whenever(speakerImage.context).thenReturn(context)
    }

    @Test
    fun itCanBindTheViewHolder() {
        val speakerViewHolder = mock<SpeakerViewHolder>()
        val qtn = mock<SpeakersRecyclerViewAdapter>()
        val subject = SpeakersRecyclerViewAdapterImpl(speakersFragmentPresenter, layoutInflaterFactory)
        val speakers = buildDefaultSpeakers()
        subject.setSpeakers(speakers, qtn)
        whenever(speakerViewHolder.view).thenReturn(view)

        subject.onBindViewHolder(speakerViewHolder, 0)

        verify(speakerViewHolder).bind(speakers[0])
        verify(qtn).notifyDataSetChanged()
    }

    @Test
    fun whenTheViewHolderIsClickedThenItNavigatesToTheDetails() {
        val speakerViewHolder = mock<SpeakerViewHolder>()
        val qtn = mock<SpeakersRecyclerViewAdapter>()
        val subject = SpeakersRecyclerViewAdapterImpl(speakersFragmentPresenter, layoutInflaterFactory)
        val speakers = buildDefaultSpeakers()
        subject.setSpeakers(speakers, qtn)
        whenever(speakerViewHolder.view).thenReturn(view)

        subject.onBindViewHolder(speakerViewHolder, 0)

        val onClickCaptor = argumentCaptor<View.OnClickListener>()
        verify(view).setOnClickListener(onClickCaptor.capture())
        val onClickListener = onClickCaptor.firstValue
        onClickListener.onClick(null)

        verify(speakersFragmentPresenter).navigateToDetails(speakers, 0)
    }

    @Test
    fun itCanCreateTheViewHolder() {
        val subject = SpeakersRecyclerViewAdapterImpl(speakersFragmentPresenter, layoutInflaterFactory)
        whenever(viewGroup.context).thenReturn(context)
        whenever(layoutInflaterFactory.inflate(context, R.layout.view_speaker, viewGroup)).thenReturn(view)

        val viewHolder = subject.onCreateViewHolder(viewGroup, 0)

        verify(layoutInflaterFactory).inflate(context, R.layout.view_speaker, viewGroup)
        assertSame(view, viewHolder.view)
    }

    @Test
    fun itCanGetItemCount() {
        val speakers = buildDefaultSpeakers()
        val qtn = mock<SpeakersRecyclerViewAdapter>()
        val subject = SpeakersRecyclerViewAdapterImpl(speakersFragmentPresenter, layoutInflaterFactory)
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
        val speaker = buildDefaultSpeakers()[0]
        whenever(picassoWrapper.with(context)).thenReturn(picassoWrapper)
        whenever(picassoWrapper.load("${speaker.GravatarUrl}?s=180&d=mm")).thenReturn(picassoWrapper)
        whenever(picassoWrapper.placeholder(R.drawable.ic_person)).thenReturn(picassoWrapper)
        whenever(picassoWrapper.resize(500, 500)).thenReturn(picassoWrapper)
        whenever(picassoWrapper.centerCrop()).thenReturn(picassoWrapper)

        val subject = SpeakerViewHolder(view, logWrapper = logWrapper, picassoWrapper = picassoWrapper)

        subject.bind(speaker)

        verify(speakerFirstName).setText(speaker.FirstName)
        verify(speakerLastName).setText(speaker.LastName)
        verify(picassoWrapper).into(speakerImage)

    }
}

