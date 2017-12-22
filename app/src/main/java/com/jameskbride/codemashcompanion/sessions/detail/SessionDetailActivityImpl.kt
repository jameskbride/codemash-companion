package com.jameskbride.codemashcompanion.sessions.detail

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivity
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailParams
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.Toaster
import java.io.Serializable
import java.text.SimpleDateFormat
import javax.inject.Inject

class SessionDetailActivityImpl @Inject constructor(
        val presenter:SessionDetailActivityPresenter,
        val speakerHeadshotFactory: SpeakerHeadshotFactory = SpeakerHeadshotFactory(),
        val intentFactory: IntentFactory = IntentFactory(),
        val toaster: Toaster = Toaster()) : SessionDetailActivityView {
    private lateinit var qtn: SessionDetailActivity

    private lateinit var removeBookmarkFAB: FloatingActionButton
    private lateinit var addBookmarkFAB: FloatingActionButton

    fun onCreate(savedInstanceState: Bundle?, qtn: SessionDetailActivity) {
        this.qtn = qtn
        presenter.view = this
        qtn.setContentView(R.layout.activity_session_detail)
        removeBookmarkFAB = qtn.findViewById(R.id.remove_bookmark_fab)
        addBookmarkFAB = qtn.findViewById(R.id.add_bookmark_fab)

        val sessionDetail: SessionDetailParam = getSessionDetailParam(qtn)

        configureActionBar(qtn)
        val session = sessionDetail.session
        configureViewForSession(session)
        configureSpeakersBlock(sessionDetail)
    }

    private fun configureViewForSession(session: FullSession) {
        configureSessionDetails(session)
        configureTimes(session)
        configureBookmarkFAB(session)
    }

    private fun configureSessionDetails(session: FullSession) {
        qtn.findViewById<TextView>(R.id.session_title).text = session.Title
        qtn.findViewById<TextView>(R.id.session_abstract).text = session.Abstract
        qtn.findViewById<TextView>(R.id.session_category).text = session.Category
        qtn.findViewById<TextView>(R.id.session_type).text = session.SessionType
        qtn.findViewById<TextView>(R.id.session_rooms).text =
                session.conferenceRooms.map { it.name }.joinToString(", ")
        qtn.findViewById<TextView>(R.id.session_tags).text =
                session.tags.map { it.name }.joinToString(", ")
    }

    private fun getSessionDetailParam(qtn: SessionDetailActivity): SessionDetailParam {
        val sessionDetail: SessionDetailParam =
                qtn.intent.getSerializableExtra(PARAMETER_BLOCK) as SessionDetailParam
        return sessionDetail
    }

    private fun configureTimes(session: FullSession) {
        val sessionStartTime = SimpleDateFormat(Session.TIMESTAMP_FORMAT).parse(session.SessionStartTime)
        val dateFormat = SimpleDateFormat("M/d/yyyy")
        val formattedDate = dateFormat.format(sessionStartTime)
        qtn.findViewById<TextView>(R.id.session_date).text = formattedDate

        val timeFormat = SimpleDateFormat("h:mm a")
        val formattedStartTime = timeFormat.format(sessionStartTime)

        val sessionEndTime = SimpleDateFormat(Session.TIMESTAMP_FORMAT).parse(session.SessionEndTime)
        val formattedEndTime = timeFormat.format(sessionEndTime)
        qtn.findViewById<TextView>(R.id.session_time).text = "${formattedStartTime} - ${formattedEndTime}"
    }

    private fun configureActionBar(qtn: SessionDetailActivity) {
        qtn.setSupportActionBar(qtn.findViewById(R.id.toolbar))
        qtn.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun configureSpeakersBlock(sessionDetail: SessionDetailParam) {
        when (sessionDetail.showSpeakers) {
            true -> presenter.retrieveSpeakers(sessionDetail.session)
            else -> qtn.findViewById<LinearLayout>(R.id.speakers_block).visibility = View.GONE
        }
    }

    private fun configureBookmarkFAB(session: FullSession) {
        removeBookmarkFAB.setOnClickListener { view: View? -> presenter.removeBookmark(session) }
        addBookmarkFAB.setOnClickListener {view: View? -> presenter.addBookmark(session) }

        when (session.isBookmarked) {
            true -> {
                removeBookmarkFAB.visibility = View.VISIBLE
                addBookmarkFAB.visibility = View.GONE
            }
            else -> {
                removeBookmarkFAB.visibility = View.GONE
                addBookmarkFAB.visibility = View.VISIBLE
            }
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

    override fun displayErrorMessage(message: Int) {
        toaster.makeText(qtn, message, Toast.LENGTH_SHORT).show()
    }

    fun onOptionsItemSelected(item: MenuItem?, qtn: SessionDetailActivity): Boolean {
        when(item?.itemId) {
            android.R.id.home ->  {
                qtn.onBackPressed()
                return true
            }
            else -> return qtn.callSuperOnOptionsItemSelected(item)
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
