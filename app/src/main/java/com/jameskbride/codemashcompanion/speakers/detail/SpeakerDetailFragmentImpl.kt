package com.jameskbride.codemashcompanion.speakers.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.PicassoLoader
import com.jameskbride.codemashcompanion.utils.UriWrapper
import javax.inject.Inject

class SpeakerDetailFragmentImpl @Inject constructor(
        val presenter: SpeakerDetailFragmentPresenter,
        val picassoLoader: PicassoLoader = PicassoLoader(),
        val intentFactory: IntentFactory = IntentFactory(),
        val uriWrapper: UriWrapper = UriWrapper(),
        val sessionHolderFactory: SessionHolderFactory = SessionHolderFactory()) : SpeakerDetailFragmentView {

    private lateinit var qtn: SpeakerDetailFragment

    fun onCreate(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, speakerDetailFragment: SpeakerDetailFragment):View {
        return inflater.inflate(R.layout.fragment_speaker_detail, container, false)
    }

    fun onViewCreated(view: View, savedInstanceState: Bundle?, speakerDetailFragment: SpeakerDetailFragment) {
        qtn = speakerDetailFragment
        presenter.view = this
        val speaker = speakerDetailFragment.arguments!!.getSerializable(SpeakerDetailFragment.SPEAKER_KEY) as FullSpeaker

        val speakerImage = view.findViewById<ImageView>(R.id.speaker_image)
        picassoLoader.load(speaker, speakerImage)

        val bioText = view.findViewById<TextView>(R.id.bio)
        bioText.text = speaker.Biography

        val firstName = view.findViewById<TextView>(R.id.speaker_first_name)
        firstName.text = speaker.FirstName

        val lastName = view.findViewById<TextView>(R.id.speaker_last_name)
        lastName.text = speaker.LastName

        configureLinks(speaker, view, speakerDetailFragment)
        presenter.retrieveSessions(speaker)
    }

    private fun configureLinks(speaker: FullSpeaker, view: View, speakerDetailFragment: SpeakerDetailFragment) {
        if (!speaker.TwitterLink.isNullOrBlank()) {
            makeNavigableLink(view, speaker.TwitterLink, speakerDetailFragment, R.id.twitter_block, R.id.twitter_link)
        }
        if (!speaker.LinkedInProfile.isNullOrBlank()) {
            makeNavigableLink(view, speaker.LinkedInProfile, speakerDetailFragment, R.id.linkedin_block, R.id.linkedin_link)
        }
        if (!speaker.BlogUrl.isNullOrBlank()) {
            makeNavigableLink(view, speaker.BlogUrl, speakerDetailFragment, R.id.blog_block, R.id.blog_link)
        }
        if (!speaker.GitHubLink.isNullOrBlank()) {
            makeNavigableLink(view, speaker.GitHubLink, speakerDetailFragment, R.id.github_block, R.id.github_link)
        }
    }

    private fun makeNavigableLink(view: View, link: String?, speakerDetailFragment: SpeakerDetailFragment, blockId:Int, linkTextId:Int) {
        val block = view.findViewById<LinearLayout>(blockId)
        block.visibility = View.VISIBLE
        block.setOnClickListener(LinkOnClickListener(link, intentFactory, speakerDetailFragment, uriWrapper))
        view.findViewById<TextView>(linkTextId).text = link
    }

    override fun displaySessions(sessions: Array<FullSession>) {
        val sessionsHolder = qtn.view!!.findViewById<LinearLayout>(R.id.sessions_holder)
        sessions.forEach { session ->
            val sessionHolder = sessionHolderFactory.make(session, qtn.context!!)
            sessionHolder.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            sessionsHolder.addView(sessionHolder)
        }
    }
}

class LinkOnClickListener constructor(val url:String?, val intentFactory: IntentFactory, val fragment: SpeakerDetailFragment, val uriWrapper: UriWrapper): View.OnClickListener {
    override fun onClick(view:View?) {
        val intent = intentFactory.make(Intent.ACTION_VIEW)
        intent.data = uriWrapper.parse(url)
        fragment.startActivity(intent)
    }
}