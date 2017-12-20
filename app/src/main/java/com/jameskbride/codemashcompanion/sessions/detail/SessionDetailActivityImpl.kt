package com.jameskbride.codemashcompanion.sessions.detail

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivity
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailParams
import com.jameskbride.codemashcompanion.utils.IntentFactory
import java.io.Serializable
import java.text.SimpleDateFormat
import javax.inject.Inject

class SessionDetailActivityImpl @Inject constructor(
        val presenter:SessionDetailActivityPresenter,
        val speakerHeadshotFactory: SpeakerHeadshotFactory = SpeakerHeadshotFactory(),
        val intentFactory: IntentFactory = IntentFactory()) : SessionDetailActivityView {
    private lateinit var qtn: SessionDetailActivity

    fun onCreate(savedInstanceState: Bundle?, qtn: SessionDetailActivity) {
        this.qtn = qtn
        presenter.view = this
        qtn.setContentView(R.layout.activity_session_detail)

        val sessionDetail:SessionDetailParam =
                qtn.intent.getSerializableExtra(PARAMETER_BLOCK) as SessionDetailParam

        qtn.findViewById<TextView>(R.id.session_title).text = sessionDetail.session.Title
        qtn.findViewById<TextView>(R.id.session_abstract).text = sessionDetail.session.Abstract
        qtn.findViewById<TextView>(R.id.session_category).text = sessionDetail.session.Category
        qtn.findViewById<TextView>(R.id.session_type).text = sessionDetail.session.SessionType
        qtn.findViewById<TextView>(R.id.session_rooms).text =
                sessionDetail.session.conferenceRooms.map { it.name }.joinToString(", ")
        qtn.findViewById<TextView>(R.id.session_tags).text =
                sessionDetail.session.tags.map { it.name }.joinToString(", ")

        val sessionStartTime = SimpleDateFormat(Session.TIMESTAMP_FORMAT).parse(sessionDetail.session.SessionStartTime)
        val dateFormat = SimpleDateFormat("M/d/yyyy")
        val formattedDate = dateFormat.format(sessionStartTime)
        qtn.findViewById<TextView>(R.id.session_date).text = formattedDate

        val timeFormat = SimpleDateFormat("h:mm a")
        val formattedStartTime = timeFormat.format(sessionStartTime)

        val sessionEndTime = SimpleDateFormat(Session.TIMESTAMP_FORMAT).parse(sessionDetail.session.SessionEndTime)
        val formattedEndTime = timeFormat.format(sessionEndTime)
        qtn.findViewById<TextView>(R.id.session_time).text = "${formattedStartTime} - ${formattedEndTime}"

        when (sessionDetail.showSpeakers) {
            true -> presenter.retrieveSpeakers(sessionDetail.session)
            else -> qtn.findViewById<LinearLayout>(R.id.speakers_block).visibility = View.GONE
        }
    }

    override fun displaySpeakers(speakers: Array<FullSpeaker>) {
        val speakersHolder = qtn.findViewById<LinearLayout>(R.id.speakers_holder)
        speakers.forEachIndexed{ index, speaker ->
            val speakerHeadshot = speakerHeadshotFactory.make(speaker, qtn)
            speakerHeadshot.layoutParams =
                    LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    )
            speakerHeadshot.setOnClickListener { view: View? -> navigateToSpeakerDetail(speakers, index) }
            speakersHolder.addView(speakerHeadshot)
        }
    }

    private fun navigateToSpeakerDetail(speakers: Array<FullSpeaker>, index:Int) {
        val intent = intentFactory.make(qtn, SpeakerDetailActivity::class.java)
        intent.putExtra(SpeakerDetailActivityImpl.PARAMETER_BLOCK, SpeakerDetailParams(speakers, index))

        qtn.startActivity(intent)
    }

    companion object {

        val PARAMETER_BLOCK:String = "PARAMETER_BLOCK"
    }
}

class SessionDetailParam(val session: FullSession, val showSpeakers:Boolean = true): Serializable
