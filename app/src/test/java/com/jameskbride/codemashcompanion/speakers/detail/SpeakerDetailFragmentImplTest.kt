package com.jameskbride.codemashcompanion.speakers.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.PicassoLoader
import com.jameskbride.codemashcompanion.utils.UriWrapper
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.*
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
    private lateinit var linkedinText:TextView
    private lateinit var githubBlock:LinearLayout
    private lateinit var githubText:TextView
    private lateinit var twitterBlock:LinearLayout
    private lateinit var twitterText:TextView
    private lateinit var blogBlock:LinearLayout
    private lateinit var blogText:TextView
    private lateinit var picassoLoader:PicassoLoader
    private lateinit var bundle:Bundle
    private lateinit var intent:Intent
    private lateinit var intentFactory:IntentFactory
    private lateinit var context:Context
    private lateinit var uriWrapper:UriWrapper
    private lateinit var uri:Uri

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
        linkedinText = mock()
        githubBlock = mock()
        githubText = mock()
        twitterBlock = mock()
        twitterText = mock()
        blogBlock = mock()
        blogText = mock()
        intent = mock()
        intentFactory = mock()
        context = mock()
        uriWrapper = mock()
        uri = mock()

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
        whenever(view.findViewById<TextView>(R.id.linkedin_link)).thenReturn(linkedinText)
        whenever(view.findViewById<LinearLayout>(R.id.github_block)).thenReturn(githubBlock)
        whenever(view.findViewById<TextView>(R.id.github_link)).thenReturn(githubText)
        whenever(view.findViewById<LinearLayout>(R.id.twitter_block)).thenReturn(twitterBlock)
        whenever(view.findViewById<TextView>(R.id.twitter_link)).thenReturn(twitterText)
        whenever(view.findViewById<LinearLayout>(R.id.blog_block)).thenReturn(blogBlock)
        whenever(view.findViewById<TextView>(R.id.blog_link)).thenReturn(blogText)
        whenever(view.context).thenReturn(context)
        whenever(uriWrapper.parse(any())).thenReturn(uri)

        subject = SpeakerDetailFragmentImpl(picassoLoader, intentFactory = intentFactory, uriWrapper = uriWrapper)
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
        verify(linkedinText, never()).setText(speaker.LinkedInProfile)
        verify(twitterText, never()).setText(speaker.TwitterLink)
        verify(githubText, never()).setText(speaker.GitHubLink)
        verify(blogText, never()).setText(speaker.BlogUrl)
    }

    @Test
    fun onViewCreatedShowsLinksWhenAvailable() {
        speaker.LinkedInProfile = "something"
        speaker.GitHubLink = "something"
        speaker.BlogUrl = "something"
        speaker.TwitterLink = "something"

        subject.onViewCreated(view, null, speakerDetailFragment = qtn)

        verify(linkedinBlock).setVisibility(View.VISIBLE)
        verify(linkedinText).setText(speaker.LinkedInProfile)
        verify(twitterBlock).setVisibility(View.VISIBLE)
        verify(twitterText).setText(speaker.TwitterLink)
        verify(githubBlock).setVisibility(View.VISIBLE)
        verify(githubText).setText(speaker.GitHubLink)
        verify(blogBlock).setVisibility(View.VISIBLE)
        verify(blogText).setText(speaker.BlogUrl)
    }

    @Test
    fun clickingOnLinkedinLinkLaunchesAnActivity() {
        speaker.LinkedInProfile = "http://linkedin"
        whenever(intentFactory.make(Intent.ACTION_VIEW)).thenReturn(intent)
        subject.onViewCreated(view, null, qtn)

        val onClickListenerCaptor = argumentCaptor<View.OnClickListener>()
        verify(linkedinBlock).setOnClickListener(onClickListenerCaptor.capture())
        onClickListenerCaptor.firstValue.onClick(null)

        verify(intentFactory).make(Intent.ACTION_VIEW)
        verify(intent).setData(uri)
        verify(qtn).startActivity(intent)
    }

    @Test
    fun clickingOnTwitterLinkLaunchesAnActivity() {
        speaker.TwitterLink = "http://twitter"
        whenever(intentFactory.make(Intent.ACTION_VIEW)).thenReturn(intent)
        subject.onViewCreated(view, null, qtn)

        val onClickListenerCaptor = argumentCaptor<View.OnClickListener>()
        verify(twitterBlock).setOnClickListener(onClickListenerCaptor.capture())
        onClickListenerCaptor.firstValue.onClick(null)

        verify(intentFactory).make(Intent.ACTION_VIEW)
        verify(intent).setData(uri)
        verify(qtn).startActivity(intent)
    }

    @Test
    fun clickingOnGithubLinkLaunchesAnActivity() {
        speaker.GitHubLink = "http://github"
        whenever(intentFactory.make(Intent.ACTION_VIEW)).thenReturn(intent)
        subject.onViewCreated(view, null, qtn)

        val onClickListenerCaptor = argumentCaptor<View.OnClickListener>()
        verify(githubBlock).setOnClickListener(onClickListenerCaptor.capture())
        onClickListenerCaptor.firstValue.onClick(null)

        verify(intentFactory).make(Intent.ACTION_VIEW)
        verify(intent).setData(uri)
        verify(qtn).startActivity(intent)
    }

    @Test
    fun clickingOnBlogLinkLaunchesAnActivity() {
        speaker.BlogUrl = "http://someblog"
        whenever(intentFactory.make(Intent.ACTION_VIEW)).thenReturn(intent)
        subject.onViewCreated(view, null, qtn)

        val onClickListenerCaptor = argumentCaptor<View.OnClickListener>()
        verify(blogBlock).setOnClickListener(onClickListenerCaptor.capture())
        onClickListenerCaptor.firstValue.onClick(null)

        verify(intentFactory).make(Intent.ACTION_VIEW)
        verify(intent).setData(uri)
        verify(qtn).startActivity(intent)
    }
}