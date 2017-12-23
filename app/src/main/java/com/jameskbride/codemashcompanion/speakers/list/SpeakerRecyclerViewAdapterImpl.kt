package com.jameskbride.codemashcompanion.speakers.list

import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.speakers.SpeakersFragmentPresenter
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory

class SpeakersRecyclerViewAdapterImpl constructor(val speakersFragmentPresenter: SpeakersFragmentPresenter, val layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory()) {
    private var speakers: Array<FullSpeaker> = arrayOf()

    fun onBindViewHolder(holder: BaseSpeakerViewHolder?, position: Int) {
        if (speakers.isNotEmpty()) {
            bindSpeakerViewHolder(holder!!, position)
        } else {
            bindEmptySpeakerViewHolder(holder)
        }
    }

    private fun bindEmptySpeakerViewHolder(holder: BaseSpeakerViewHolder?) {
        val emptySpeakerViewHolder = holder as EmptySpeakerViewHolder
        emptySpeakerViewHolder.bind()
    }

    private fun bindSpeakerViewHolder(viewHolder: BaseSpeakerViewHolder, position: Int) {
        val speakerViewHolder = viewHolder as SpeakerViewHolder
        speakerViewHolder!!.bind(speakers[position])
        speakerViewHolder!!.view!!.setOnClickListener({ view ->
            speakersFragmentPresenter.navigateToDetails(speakers, position)
        })
    }

    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseSpeakerViewHolder {
        if (speakers.isNotEmpty()) {
            return createSpeakerViewHolder(parent)
        }

        return createEmptySpeakerViewHolder(parent)
    }

    private fun createEmptySpeakerViewHolder(parent: ViewGroup?): EmptySpeakerViewHolder {
        val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.no_data, parent)

        return EmptySpeakerViewHolder(view)
    }

    private fun createSpeakerViewHolder(parent: ViewGroup?): SpeakerViewHolder {
        val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.view_speaker, parent)

        return SpeakerViewHolder(view)
    }

    fun getItemCount(): Int {
        return if (speakers.isNotEmpty()) { speakers.size } else { 1 }
    }

    fun setSpeakers(speakers: Array<FullSpeaker>, speakersRecyclerViewAdapter: SpeakersRecyclerViewAdapter) {
        this.speakers = speakers
        speakersRecyclerViewAdapter.notifyDataSetChanged()
    }

    fun getItemViewType(position: Int): Int {
        return if (speakers.isNotEmpty()) { SpeakerListItem.SPEAKER_TYPE } else { SpeakerListItem.EMPTY_TYPE }
    }
}