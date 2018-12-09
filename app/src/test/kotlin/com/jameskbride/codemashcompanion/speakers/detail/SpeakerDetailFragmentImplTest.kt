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
import android.widget.Toast
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl.Companion.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailActivity
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailParam
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.PicassoLoader
import com.jameskbride.codemashcompanion.utils.Toaster
import com.jameskbride.codemashcompanion.utils.UriWrapper
import com.jameskbride.codemashcompanion.utils.test.buildDefaultFullSpeakers
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SpeakerDetailFragmentImplTest {

    @Mock private lateinit var layoutInflater:LayoutInflater
    @Mock private lateinit var viewGroup:ViewGroup
    @Mock private lateinit var view:View
    @Mock private lateinit var qtn:SpeakerDetailFragment
    @Mock private lateinit var speakerImage:ImageView
    @Mock private lateinit var bioText:TextView
    @Mock private lateinit var firstName:TextView
    @Mock private lateinit var lastName:TextView
    @Mock private lateinit var linkedinBlock:LinearLayout
    @Mock private lateinit var linkedinText:TextView
    @Mock private lateinit var githubBlock:LinearLayout
    @Mock private lateinit var githubText:TextView
    @Mock private lateinit var twitterBlock:LinearLayout
    @Mock private lateinit var twitterText:TextView
    @Mock private lateinit var blogBlock:LinearLayout
    @Mock private lateinit var blogText:TextView
    @Mock private lateinit var picassoLoader:PicassoLoader
    @Mock private lateinit var bundle:Bundle
    @Mock private lateinit var intent:Intent
    @Mock private lateinit var intentFactory:IntentFactory
    @Mock private lateinit var context:Context
    @Mock private lateinit var uriWrapper:UriWrapper
    @Mock private lateinit var uri:Uri
    @Mock private lateinit var presenter: SpeakerDetailFragmentPresenter
    @Mock private lateinit var sessionsHolder:LinearLayout
    @Mock private lateinit var sessionHolderFactory:SessionHolderFactory
    @Mock private lateinit var activity:AppCompatActivity
    @Mock private lateinit var toaster:Toaster

    private lateinit var speaker: FullSpeaker

    private lateinit var subject:SpeakerDetailFragmentImpl

    @Before
    fun setUp() {
        initMocks(this)
        speaker = buildDefaultFullSpeakers()[0]
        speaker.LinkedInProfile = ""
        speaker.BlogUrl = ""
        speaker.GitHubLink = ""
        speaker.TwitterLink = ""

        whenever(qtn.arguments).thenReturn(bundle)
        whenever(qtn.view).thenReturn(view)
        whenever(qtn.context).thenReturn(context)
        whenever(qtn.activity).thenReturn(activity)
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
        whenever(view.findViewById<LinearLayout>(R.id.sessions_holder)).thenReturn(sessionsHolder)
        whenever(view.context).thenReturn(context)
        whenever(uriWrapper.parse(any())).thenReturn(uri)
        whenever(bundle.getSerializable(SpeakerDetailFragment.SPEAKER_KEY)).thenReturn(speaker)

        subject = SpeakerDetailFragmentImpl(
                presenter = presenter,
                picassoLoader = picassoLoader,
                intentFactory = intentFactory,
                uriWrapper = uriWrapper,
                sessionHolderFactory = sessionHolderFactory,
                toaster = toaster)
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
    fun onViewCreatedRequestsTheSpeakers() {
        subject.onViewCreated(view, null, qtn)

        verify(presenter).retrieveSessions(speaker)
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
    fun itCanDisplayAnErrorMessage() {
        whenever(toaster.makeText(activity, R.string.unexpected_error, Toast.LENGTH_SHORT)).thenReturn(toaster)
        subject.onViewCreated(view, null, speakerDetailFragment = qtn)

        subject.displayErrorMessage(R.string.unexpected_error)

        verify(toaster).show()
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

    @Test
    fun itCanDisplaySessions() {
        subject.onViewCreated(view, null, qtn)

        val sessions = arrayOf(
                FullSession("1"),
                FullSession("2")
        )

        val sessionHolder = mock<SessionHolder>()
        whenever(sessionHolderFactory.make(any(), eq(context))).thenReturn(sessionHolder)

        subject.displaySessions(sessions)

        verify(sessionHolderFactory, times(2)).make(any(), eq(context))
        verify(sessionHolder, times(2)).setLayoutParams(any<LinearLayout.LayoutParams>())

        verify(sessionsHolder, times(2)).addView(sessionHolder)
    }

    @Test
    fun sessionHolderNavigatesToSessionDetailOnClick() {
        subject.onViewCreated(view, null, qtn)

        val sessions = arrayOf(
                FullSession("1"),
                FullSession("2")
        )

        val sessionHolder = mock<SessionHolder>()
        whenever(sessionHolderFactory.make(any(), eq(context))).thenReturn(sessionHolder)

        subject.displaySessions(sessions)

        val onClickCaptor = argumentCaptor<View.OnClickListener>()
        verify(sessionHolder, atLeastOnce()).setOnClickListener(onClickCaptor.capture())

        whenever(intentFactory.make(context, SessionDetailActivity::class.java)).thenReturn(intent)

        onClickCaptor.firstValue.onClick(null)

        val extraCaptor = argumentCaptor<SessionDetailParam>()
        verify(intent, atLeastOnce()).putExtra(eq(PARAMETER_BLOCK), extraCaptor.capture())
        val sessionDetailParam = extraCaptor.firstValue
        assertEquals(sessions[0].Id, sessionDetailParam.sessionId)
        assertFalse(sessionDetailParam.showSpeakers)

        verify(qtn).startActivity(intent)
    }
}