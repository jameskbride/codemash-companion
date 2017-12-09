package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import com.jameskbride.codemashcompanion.utils.PicassoLoader
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class SpeakerDetailFragmentImplTest {

    private lateinit var layoutInflater:LayoutInflater
    private lateinit var viewGroup:ViewGroup
    private lateinit var view:View
    private lateinit var qtn:SpeakerDetailFragment
    private lateinit var speakerImage:ImageView
    private lateinit var bioText:TextView
    private lateinit var picassoLoader:PicassoLoader
    private lateinit var bundle:Bundle

    private lateinit var speaker:Speaker

    private lateinit var subject:SpeakerDetailFragmentImpl

    @Before
    fun setUp() {
        layoutInflater = mock()
        viewGroup = mock()
        view = mock()
        qtn = mock()
        picassoLoader = mock()
        speakerImage = mock()
        bundle = mock()
        bioText = mock()

        speaker = buildDefaultSpeakers()[0]

        whenever(qtn.arguments).thenReturn(bundle)
        whenever(bundle.getSerializable(SpeakerDetailFragment.SPEAKER_KEY)).thenReturn(speaker)
        whenever(view.findViewById<ImageView>(R.id.speaker_image)).thenReturn(speakerImage)
        whenever(view.findViewById<TextView>(R.id.bio)).thenReturn(bioText)

        subject = SpeakerDetailFragmentImpl(picassoLoader)
    }

    @Test
    fun onCreateInflatesTheView() {
        whenever(layoutInflater.inflate(R.layout.fragment_speaker_detail, viewGroup, false)).thenReturn(view)

        val result = subject.onCreate(layoutInflater, viewGroup, null, qtn)

        verify(layoutInflater).inflate(R.layout.fragment_speaker_detail, viewGroup, false)
        assertSame(view, result)
    }

    @Test
    fun onViewCreatedLoadsTheSpeakerImage() {
        subject.onViewCreated(view, null, speakerDetailFragment = qtn)

        verify(picassoLoader).load(speaker, speakerImage)
    }

    @Test
    fun onViewCreatedSetsTheBioText() {
        subject.onViewCreated(view, null, speakerDetailFragment = qtn)

        verify(bioText).setText(speaker.Biography)
    }
}