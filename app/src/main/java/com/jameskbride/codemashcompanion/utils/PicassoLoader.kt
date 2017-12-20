package com.jameskbride.codemashcompanion.utils

import android.widget.ImageView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.Speaker
import com.squareup.picasso.Transformation

class PicassoLoader constructor(val picassoWrapper: PicassoWrapper = PicassoWrapper(), val logWrapper: LogWrapper = LogWrapper()) {

    fun load(speaker: Speaker, speakerImage: ImageView, width:Int = 500, height:Int = 500, transformation: Transformation? = null) {
        val url = "${speaker.GravatarUrl}?s=180&d=mm"
        logWrapper.d("PicassoLoader", "Requesting gravatar url: ${url}")
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