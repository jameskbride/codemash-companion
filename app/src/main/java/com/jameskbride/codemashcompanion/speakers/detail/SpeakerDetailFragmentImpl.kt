package com.jameskbride.codemashcompanion.speakers.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

class SpeakerDetailFragmentImpl constructor(val picassoLoader: PicassoLoader = PicassoLoader(), val intentFactory: IntentFactory = IntentFactory(), val uriWrapper: UriWrapper = UriWrapper()) {

    fun onCreate(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, speakerDetailFragment: SpeakerDetailFragment):View {
        return inflater.inflate(R.layout.fragment_speaker_detail, container, false)
    }

    fun onViewCreated(view: View, savedInstanceState: Bundle?, speakerDetailFragment: SpeakerDetailFragment) {
        val speaker = speakerDetailFragment.arguments!!.getSerializable(SpeakerDetailFragment.SPEAKER_KEY) as Speaker

        val speakerImage = view.findViewById<ImageView>(R.id.speaker_image)
        picassoLoader.load(speaker, speakerImage)

        val bioText = view.findViewById<TextView>(R.id.bio)
        bioText.text = speaker.Biography

        val firstName = view.findViewById<TextView>(R.id.speaker_first_name)
        firstName.text = speaker.FirstName

        val lastName = view.findViewById<TextView>(R.id.speaker_last_name)
        lastName.text = speaker.LastName

        configureLinks(speaker, view, speakerDetailFragment)
    }

    private fun configureLinks(speaker: Speaker, view: View, speakerDetailFragment: SpeakerDetailFragment) {
        if (!speaker.TwitterLink.isNullOrBlank()) {
            val twitterBlock = view.findViewById<LinearLayout>(R.id.twitter_block)
            twitterBlock.visibility = View.VISIBLE
            twitterBlock.setOnClickListener(LinkOnClickListener(speaker.TwitterLink, intentFactory, speakerDetailFragment, uriWrapper))
            view.findViewById<TextView>(R.id.twitter_link).text = speaker.TwitterLink
        }
        if (!speaker.LinkedInProfile.isNullOrBlank()) {
            val linkedinBlock = view.findViewById<LinearLayout>(R.id.linkedin_block)
            linkedinBlock.visibility = View.VISIBLE
            linkedinBlock.setOnClickListener(LinkOnClickListener(speaker.LinkedInProfile, intentFactory, speakerDetailFragment, uriWrapper))
            view.findViewById<TextView>(R.id.linkedin_link).text = speaker.LinkedInProfile
        }
        if (!speaker.BlogUrl.isNullOrBlank()) {
            val blogBlock = view.findViewById<LinearLayout>(R.id.blog_block)
            blogBlock.visibility = View.VISIBLE
            blogBlock.setOnClickListener(LinkOnClickListener(speaker.BlogUrl, intentFactory, speakerDetailFragment, uriWrapper))
            view.findViewById<TextView>(R.id.blog_link).text = speaker.BlogUrl
        }
        if (!speaker.GitHubLink.isNullOrBlank()) {
            val githubBlock = view.findViewById<LinearLayout>(R.id.github_block)
            githubBlock.visibility = View.VISIBLE
            githubBlock.setOnClickListener(LinkOnClickListener(speaker.GitHubLink, intentFactory, speakerDetailFragment, uriWrapper))
            view.findViewById<TextView>(R.id.github_link).text = speaker.GitHubLink
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