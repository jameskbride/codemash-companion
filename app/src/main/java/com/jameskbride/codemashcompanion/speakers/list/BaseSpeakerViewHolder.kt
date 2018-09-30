package com.jameskbride.codemashcompanion.speakers.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.utils.PicassoLoader


open class BaseSpeakerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var view = itemView
}

class EmptySpeakerViewHolder(itemView: View): BaseSpeakerViewHolder(itemView) {
    fun bind() {
    }
}

class SpeakerViewHolder(itemView: View, val picassoLoader: PicassoLoader = PicassoLoader()) : BaseSpeakerViewHolder(itemView) {
    var speakerImage: ImageView?
    var speakerFirstName: TextView?
    var speakerLastName: TextView?

    init {
        speakerImage = itemView?.findViewById(R.id.speaker_image)
        speakerFirstName = itemView?.findViewById(R.id.speaker_first_name)
        speakerLastName = itemView?.findViewById(R.id.speaker_last_name)
    }

    fun bind(speaker: FullSpeaker) {
        speakerFirstName!!.text = speaker.FirstName
        speakerLastName!!.text = speaker.LastName

        picassoLoader.load(speaker, speakerImage!!)
    }
}