package com.jameskbride.codemashcompanion.utils

import android.widget.ImageView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.squareup.picasso.Transformation

class PicassoLoader constructor(val picassoWrapper: PicassoWrapper = PicassoWrapper(), val logWrapper: LogWrapper = LogWrapper()) {

    fun load(speaker: FullSpeaker, speakerImage: ImageView, width:Int = 500, height:Int = 500, transformation: Transformation? = null) {
        val url = "${speaker.GravatarUrl}"
        logWrapper.d("PicassoLoader", "Requesting url: ${url}")
        picassoWrapper.with(speakerImage.context)
                .load(url)
                .placeholder(R.drawable.ic_person)
                .resize(width, height)
                .centerCrop()

        if (transformation != null) {
            picassoWrapper.transform(transformation)
        }

        picassoWrapper.into(speakerImage)
    }
}