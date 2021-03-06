package com.jameskbride.codemashcompanion.sessions.detail

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import com.jameskbride.codemashcompanion.data.model.FullSpeaker

class SpeakerHeadshot :LinearLayout {

    val impl:SpeakerHeadshotImpl = SpeakerHeadshotImpl()

    @JvmOverloads
    constructor(
            speaker:FullSpeaker,
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        impl.onInflate(speaker, context, this)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            speaker: FullSpeaker,
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        impl.onInflate(speaker, context, this)
    }
}

class SpeakerHeadshotFactory {
    fun make(speaker: FullSpeaker, context: Context): SpeakerHeadshot {
        return SpeakerHeadshot(speaker, context)
    }
}