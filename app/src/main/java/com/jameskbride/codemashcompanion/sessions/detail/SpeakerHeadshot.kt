package com.jameskbride.codemashcompanion.sessions.detail

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.Speaker
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.jameskbride.codemashcompanion.utils.PicassoLoader

class SpeakerHeadshot :LinearLayout {

    @JvmOverloads
    constructor(
            speaker:Speaker,
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        onInflate(speaker, context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            speaker: Speaker,
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        onInflate(speaker, context)
    }

    fun onInflate(speaker: Speaker, context: Context,
                  layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory(),
                  picassoLoader: PicassoLoader = PicassoLoader()) {
        val view = layoutInflaterFactory.inflate(context, R.layout.view_speaker_headshot, this, false)
        val headshotImageView = view?.findViewById<ImageView>(R.id.speaker_headshot)
        picassoLoader.load(speaker, headshotImageView!!)
    }
}