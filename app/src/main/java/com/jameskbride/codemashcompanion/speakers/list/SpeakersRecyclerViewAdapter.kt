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
    : RecyclerView.Adapter<BaseSpeakerViewHolder>() {

    override fun onBindViewHolder(holder: BaseSpeakerViewHolder?, position: Int) {
        impl.onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseSpeakerViewHolder {
        return impl.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return impl.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return impl.getItemViewType(position)
    }

    fun setSpeakers(speakers:Array<FullSpeaker>) {
        impl.setSpeakers(speakers, this)
    }
}

class SpeakersViewAdapterFactory {
    fun make(speakersFragmentPresenter: SpeakersFragmentPresenter): SpeakersRecyclerViewAdapter {
        return SpeakersRecyclerViewAdapter(speakersFragmentPresenter)
    }
}