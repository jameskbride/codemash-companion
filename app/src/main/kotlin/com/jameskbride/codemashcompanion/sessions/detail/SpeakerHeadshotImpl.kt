package com.jameskbride.codemashcompanion.sessions.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.utils.CircleTransform
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.jameskbride.codemashcompanion.utils.PicassoLoader

class SpeakerHeadshotImpl {
    fun onInflate(speaker: FullSpeaker,
                  context: Context,
                  qtn:SpeakerHeadshot,
                  attrs: AttributeSet? = null,
                  defStyleAttr: Int = 0, layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory(),
                  picassoLoader: PicassoLoader = PicassoLoader()) {
        val view = layoutInflaterFactory.inflate(context, R.layout.view_speaker_headshot, qtn, true)
        val headshotImageView = view?.findViewById<ImageView>(R.id.speaker_headshot)
        picassoLoader.load(speaker, headshotImageView!!, width = 250, height = 250, transformation = CircleTransform())
        view.findViewById<TextView>(R.id.speaker_first_name).text = speaker.FirstName
        view.findViewById<TextView>(R.id.speaker_last_name).text = speaker.LastName
    }
}