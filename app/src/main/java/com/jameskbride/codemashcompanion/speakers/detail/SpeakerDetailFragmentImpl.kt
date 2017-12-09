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

class SpeakerDetailFragmentImpl constructor(val picassoLoader: PicassoLoader = PicassoLoader()) {

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

        if (!speaker.TwitterLink.isNullOrBlank()) { view.findViewById<LinearLayout>(R.id.twitter_block).visibility = View.VISIBLE }
        if (!speaker.LinkedInProfile.isNullOrBlank()) { view.findViewById<LinearLayout>(R.id.linkedin_block).visibility = View.VISIBLE }
        if (!speaker.BlogUrl.isNullOrBlank()) { view.findViewById<LinearLayout>(R.id.blog_block).visibility = View.VISIBLE }
        if (!speaker.GitHubLink.isNullOrBlank()) { view.findViewById<LinearLayout>(R.id.github_block).visibility = View.VISIBLE }
    }
}