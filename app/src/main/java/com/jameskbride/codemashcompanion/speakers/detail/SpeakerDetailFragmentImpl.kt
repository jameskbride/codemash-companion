package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R

class SpeakerDetailFragmentImpl {
    fun onCreate(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, speakerDetailFragment: SpeakerDetailFragment):View {

        return inflater.inflate(R.layout.fragment_speaker_detail, container, false)
    }
}