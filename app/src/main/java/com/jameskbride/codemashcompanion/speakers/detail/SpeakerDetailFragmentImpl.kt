package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    }
}