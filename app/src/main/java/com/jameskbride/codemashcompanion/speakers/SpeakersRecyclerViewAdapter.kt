package com.jameskbride.codemashcompanion.speakers

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Speaker
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.jameskbride.codemashcompanion.utils.LogWrapper
import com.jameskbride.codemashcompanion.utils.PicassoWrapper

class SpeakersRecyclerViewAdapter constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter, val impl: SpeakersRecyclerViewAdapterImpl = SpeakersRecyclerViewAdapterImpl(speakersFragmentPresenter))
    : RecyclerView.Adapter<SpeakerViewHolder>() {

    override fun onBindViewHolder(holder: SpeakerViewHolder?, position: Int) {
        impl.onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SpeakerViewHolder {
        return impl.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return impl.getItemCount()
    }

    fun setSpeakers(speakers:Array<Speaker>) {
        impl.setSpeakers(speakers, this)
    }
}

class SpeakersRecyclerViewAdapterImpl constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter, val layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory()) {
    private var speakers: Array<Speaker> = arrayOf()

    fun onBindViewHolder(holder: SpeakerViewHolder?, position: Int) {
        holder!!.bind(speakers[position])
        holder!!.view!!.setOnClickListener( {view ->
            speakersFragmentPresenter.navigateToDetails(speakers, position)
        })
    }

    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SpeakerViewHolder {
        val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.view_speaker, parent)

        return SpeakerViewHolder(view)
    }

    fun getItemCount(): Int {
        return speakers.size
    }

    fun setSpeakers(speakers:Array<Speaker>, speakersRecyclerViewAdapter: SpeakersRecyclerViewAdapter) {
        this.speakers = speakers
        speakersRecyclerViewAdapter.notifyDataSetChanged()
    }
}

class SpeakerViewHolder(itemView: View?, val logWrapper: LogWrapper = LogWrapper(), val picassoWrapper: PicassoWrapper = PicassoWrapper()) : RecyclerView.ViewHolder(itemView) {
    var speakerImage:ImageView?
    var speakerFirstName:TextView?
    var speakerLastName:TextView?
    var view: View?

    init {
        view = itemView
        speakerImage = itemView?.findViewById(R.id.speaker_image)
        speakerFirstName = itemView?.findViewById(R.id.speaker_first_name)
        speakerLastName = itemView?.findViewById(R.id.speaker_last_name)
    }

    fun bind(speaker: Speaker) {
        speakerFirstName!!.text = speaker.FirstName
        speakerLastName!!.text = speaker.LastName

        val url = "${speaker.GravatarUrl}?s=180&d=mm"
        logWrapper.d("SpeakersRecyclerViewAdapter", "Requesting gravatar url: ${url}")
        picassoWrapper.with(speakerImage!!.context)
                .load(url)
                .placeholder(R.drawable.ic_person_black)
                .resize(500, 500)
                .centerCrop()
                .into(speakerImage)
    }
}

class SpeakersViewAdapterFactory {
    fun make(speakersFragmentPresenter: SpeakersFragmentPresenter): SpeakersRecyclerViewAdapter {
        return SpeakersRecyclerViewAdapter(speakersFragmentPresenter)
    }

}