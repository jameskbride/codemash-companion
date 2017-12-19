package com.jameskbride.codemashcompanion.sessions.detail

import android.os.Bundle
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.data.model.Speaker
import java.io.Serializable
import java.text.SimpleDateFormat
import javax.inject.Inject

class SessionDetailActivityImpl @Inject constructor(val presenter:SessionDetailActivityPresenter) : SessionDetailActivityView {
    fun onCreate(savedInstanceState: Bundle?, qtn: SessionDetailActivity) {
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

        presenter.retrieveSpeakers(sessionDetail.session)
    }

    override fun displaySpeakers(speakers: Array<Speaker>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class SessionDetailParam(val session: FullSession): Serializable

    companion object {
        val PARAMETER_BLOCK:String = "PARAMETER_BLOCK"
    }
}