package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jameskbride.codemashcompanion.network.Speaker
import com.jameskbride.codemashcompanion.utils.LogWrapper
import com.jameskbride.codemashcompanion.utils.PicassoWrapper


class SpeakersViewAdapterImpl constructor(val context: Context,
                                          val picassoWrapper: PicassoWrapper = PicassoWrapper(), val logWrapper: LogWrapper = LogWrapper()) {
    private var speakers: Array<Speaker> = arrayOf()

    fun getView(position: Int, convertView: View?, parentView: ViewGroup?,
                speakerViewAdapter: SpeakersViewAdapter): View {

        val imageView = convertView ?: speakerViewAdapter.buildImageView()

        val url = "${speakers[position].GravatarUrl}?s=180&d=mm"
        logWrapper.d("SpeakersViewAdapterImpl", "Requesting gravatar url: ${url}")
        picassoWrapper.with(context)
                .load(url)
                .resize(500, 500)
                .centerCrop()
                .into(imageView as ImageView)

        return imageView
    }

    fun getItem(position: Int): Any {
        return speakers[position]
    }

    fun getItemId(p0: Int): Long {
        return 0
    }

    fun getCount(): Int {
        return speakers.size
    }

    fun setSpeakers(speakers: Array<Speaker>) {
        this.speakers = speakers
    }
}