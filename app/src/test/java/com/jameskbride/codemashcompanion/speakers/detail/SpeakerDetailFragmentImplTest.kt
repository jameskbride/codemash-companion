package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import com.jameskbride.codemashcompanion.utils.PicassoLoader
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
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
    private lateinit var firstName:TextView
    private lateinit var lastName:TextView
    private lateinit var linkedinBlock:LinearLayout
    private lateinit var githubBlock:LinearLayout
    private lateinit var twitterBlock:LinearLayout
    private lateinit var blogBlock:LinearLayout
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
        firstName = mock()
        lastName = mock()
        linkedinBlock = mock()
        githubBlock = mock()
        twitterBlock = mock()
        blogBlock = mock()

        speaker = buildDefaultSpeakers()[0]
        speaker.LinkedInProfile = ""
        speaker.BlogUrl = ""
        speaker.GitHubLink = ""
        speaker.TwitterLink = ""

        whenever(qtn.arguments).thenReturn(bundle)
        whenever(bundle.getSerializable(SpeakerDetailFragment.SPEAKER_KEY)).thenReturn(speaker)
        whenever(view.findViewById<ImageView>(R.id.speaker_image)).thenReturn(speakerImage)
        whenever(view.findViewById<TextView>(R.id.bio)).thenReturn(bioText)
        whenever(view.findViewById<TextView>(R.id.speaker_first_name)).thenReturn(firstName)
        whenever(view.findViewById<TextView>(R.id.speaker_last_name)).thenReturn(lastName)
        whenever(view.findViewById<LinearLayout>(R.id.linkedin_block)).thenReturn(linkedinBlock)
        whenever(view.findViewById<LinearLayout>(R.id.github_block)).thenReturn(githubBlock)
        whenever(view.findViewById<LinearLayout>(R.id.twitter_block)).thenReturn(twitterBlock)
        whenever(view.findViewById<LinearLayout>(R.id.blog_block)).thenReturn(blogBlock)


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

    @Test
    fun onViewCreatedSetsTheSpeakerFirstAndLastNames() {
        subject.onViewCreated(view, null, speakerDetailFragment = qtn)

        verify(firstName).setText(speaker.FirstName)
        verify(lastName).setText(speaker.LastName)
    }

    @Test
    fun onViewCreatedDoesNotShowLinksWhenUnavailable() {
        subject.onViewCreated(view, null, speakerDetailFragment = qtn)

        verify(linkedinBlock, never()).setVisibility(View.VISIBLE)
        verify(twitterBlock, never()).setVisibility(View.VISIBLE)
        verify(githubBlock, never()).setVisibility(View.VISIBLE)
        verify(blogBlock, never()).setVisibility(View.VISIBLE)
    }

    @Test
    fun onViewCreatedShowsLinksWhenAvailable() {
        speaker.LinkedInProfile = "something"
        speaker.GitHubLink = "something"
        speaker.BlogUrl = "something"
        speaker.TwitterLink = "something"

        subject.onViewCreated(view, null, speakerDetailFragment = qtn)

        verify(linkedinBlock).setVisibility(View.VISIBLE)
        verify(twitterBlock).setVisibility(View.VISIBLE)
        verify(githubBlock).setVisibility(View.VISIBLE)
        verify(blogBlock).setVisibility(View.VISIBLE)
    }
}