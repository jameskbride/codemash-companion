package com.jameskbride.codemashcompanion.sessions.detail

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.framework.BaseActivity
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl
import com.jameskbride.codemashcompanion.rooms.RoomActivity
import com.jameskbride.codemashcompanion.rooms.RoomParams
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivity
import com.jameskbride.codemashcompanion.speakers.detail.SpeakersParams
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.Toaster
import java.io.Serializable
import java.text.SimpleDateFormat
import javax.inject.Inject

class SessionDetailActivityImpl @Inject constructor(
        val presenter:SessionDetailActivityPresenter,
        val speakerHeadshotFactory: SpeakerHeadshotFactory = SpeakerHeadshotFactory(),
        val intentFactory: IntentFactory = IntentFactory(),
        val toaster: Toaster = Toaster()) : SessionDetailActivityView, BaseActivityImpl() {
    private lateinit var qtn: SessionDetailActivity

    private lateinit var removeBookmarkFAB: FloatingActionButton
    private lateinit var addBookmarkFAB: FloatingActionButton

    private lateinit var sessionDetail: SessionDetailParam

    override fun onCreate(savedInstanceState: Bundle?, qtn: BaseActivity) {
        this.qtn = qtn as SessionDetailActivity
        presenter.view = this
        qtn.setContentView(R.layout.activity_session_detail)
        setTitle(qtn, R.string.session_detail)
        configureActionBar(qtn)
        removeBookmarkFAB = qtn.findViewById(R.id.remove_bookmark_fab)
        addBookmarkFAB = qtn.findViewById(R.id.add_bookmark_fab)

        sessionDetail = getSessionDetailParam(qtn)
    }

    override fun configureForSession(session: FullSession) {
        configureSessionDetails(session)
        configureRooms(session)
        configureTimes(session)
        configureBookmarkFAB(session)
        configureSpeakersBlock(session.Id, sessionDetail.showSpeakers)
    }

    private fun configureRooms(session: FullSession) {
        qtn.findViewById<TextView>(R.id.session_rooms).text =
                session.conferenceRooms.map { it.name }.joinToString(", ")
        qtn.findViewById<LinearLayout>(R.id.rooms_block).setOnClickListener { view: View? ->
            navigateToRooms(session.conferenceRooms)
        }
    }

    private fun navigateToRooms(conferenceRooms: List<ConferenceRoom>) {
        presenter.navigateToMap(conferenceRooms)
//        val intent = intentFactory.make(qtn, RoomActivity::class.java)
//        intent.putExtra(PARAMETER_BLOCK, RoomParams(rooms = conferenceRooms))
//        qtn.startActivity(intent)
    }

    private fun configureSessionDetails(session: FullSession) {
        qtn.findViewById<TextView>(R.id.session_title).text = session.Title
        qtn.findViewById<TextView>(R.id.session_abstract).text = session.Abstract
        qtn.findViewById<TextView>(R.id.session_category).text = session.Category
        qtn.findViewById<TextView>(R.id.session_type).text = session.SessionType
        qtn.findViewById<TextView>(R.id.session_tags).text =
                session.tags.map { it.name }.joinToString(", ")
    }

    private fun getSessionDetailParam(qtn: SessionDetailActivity): SessionDetailParam {
        return qtn.intent.getSerializableExtra(PARAMETER_BLOCK) as SessionDetailParam
    }

    private fun configureTimes(session: FullSession) {
        val sessionStartTime = SimpleDateFormat(Session.TIMESTAMP_FORMAT).parse(session.SessionStartTime)
        val dateFormat = SimpleDateFormat(Session.SHORT_DATE_FORMAT)
        val formattedDate = dateFormat.format(sessionStartTime)
        qtn.findViewById<TextView>(R.id.session_date).text = formattedDate

        val timeFormat = SimpleDateFormat("h:mm a")
        val formattedStartTime = timeFormat.format(sessionStartTime)

        val sessionEndTime = SimpleDateFormat(Session.TIMESTAMP_FORMAT).parse(session.SessionEndTime)
        val formattedEndTime = timeFormat.format(sessionEndTime)
        qtn.findViewById<TextView>(R.id.session_time).text = "${formattedStartTime} - ${formattedEndTime}"
    }

    private fun configureSpeakersBlock(sessionId: String, showSpeakers:Boolean) {
        when (showSpeakers) {
            true -> presenter.retrieveSpeakers(sessionId)
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
        speakersHolder.removeAllViews()
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

    private fun navigateToSpeakerDetail(speakers: Array<FullSpeaker>, index:Int) {
        val intent = intentFactory.make(qtn, SpeakerDetailActivity::class.java)
        intent.putExtra(PARAMETER_BLOCK, SpeakersParams(speakers, index))

        qtn.startActivity(intent)
    }

    override fun onResume(sessionDetailActivity: BaseActivity) {
        presenter.open()
        presenter.retrieveSession(sessionId = sessionDetail.sessionId)
    }

    override fun onPause(sessionDetailActivity: BaseActivity) {
        presenter.close()
    }
}

class SessionDetailParam(val showSpeakers: Boolean = true, val sessionId: String = ""): Serializable
