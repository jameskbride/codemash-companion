package com.jameskbride.codemashcompanion.utils

import android.widget.ImageView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker

class PicassoLoader constructor(val picassoWrapper: PicassoWrapper = PicassoWrapper(), val logWrapper: LogWrapper = LogWrapper()) {

    fun load(speaker:Speaker, speakerImage: ImageView) {
        val url = "${speaker.GravatarUrl}?s=180&d=mm"
        logWrapper.d("PicassoLoader", "Requesting gravatar url: ${url}")
        picassoWrapper.with(speakerImage.context)
                .load(url)
                .placeholder(R.drawable.ic_person)
                .resize(500, 500)
                .centerCrop()
                .into(speakerImage)
    }
}