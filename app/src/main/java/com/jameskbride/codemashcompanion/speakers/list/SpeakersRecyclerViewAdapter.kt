package com.jameskbride.codemashcompanion.speakers.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.speakers.SpeakersFragmentPresenter
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.jameskbride.codemashcompanion.utils.PicassoLoader

class SpeakersRecyclerViewAdapter constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter,
                                              val impl: SpeakersRecyclerViewAdapterImpl = SpeakersRecyclerViewAdapterImpl(speakersFragmentPresenter))
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

    fun setSpeakers(speakers:Array<FullSpeaker>) {
        impl.setSpeakers(speakers, this)
    }
}

class SpeakersRecyclerViewAdapterImpl constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter, val layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory()) {
    private var speakers: Array<FullSpeaker> = arrayOf()

    fun onBindViewHolder(holder: SpeakerViewHolder?, position: Int) {
        holder!!.bind(speakers[position])
        holder!!.view!!.setOnClickListener({ view ->
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

    fun setSpeakers(speakers: Array<FullSpeaker>, speakersRecyclerViewAdapter: SpeakersRecyclerViewAdapter) {
        this.speakers = speakers
        speakersRecyclerViewAdapter.notifyDataSetChanged()
    }
}

class SpeakerViewHolder(itemView: View?, val picassoLoader: PicassoLoader = PicassoLoader()) : RecyclerView.ViewHolder(itemView) {
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

    fun bind(speaker: FullSpeaker) {
        speakerFirstName!!.text = speaker.FirstName
        speakerLastName!!.text = speaker.LastName

        picassoLoader.load(speaker, speakerImage!!)
    }
}

class SpeakersViewAdapterFactory {
    fun make(speakersFragmentPresenter: SpeakersFragmentPresenter): SpeakersRecyclerViewAdapter {
        return SpeakersRecyclerViewAdapter(speakersFragmentPresenter)
    }
}