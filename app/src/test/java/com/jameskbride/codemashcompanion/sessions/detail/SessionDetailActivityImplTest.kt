package com.jameskbride.codemashcompanion.sessions.detail

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.data.model.Tag
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivity
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailParams
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SessionDetailActivityImplTest {

    @Mock private lateinit var qtn: SessionDetailActivity
    @Mock private lateinit var intent:Intent
    @Mock private lateinit var category:TextView
    @Mock private lateinit var sessionTime:TextView
    @Mock private lateinit var sessionType:TextView
    @Mock private lateinit var title:TextView
    @Mock private lateinit var abstract:TextView
    @Mock private lateinit var rooms:TextView
    @Mock private lateinit var tags:TextView
    @Mock private lateinit var sessionDate:TextView
    @Mock private lateinit var speakersHolder:LinearLayout
    @Mock private lateinit var speakersBlock:LinearLayout
    @Mock private lateinit var presenter:SessionDetailActivityPresenter
    @Mock private lateinit var speakerHeadshotFactory:SpeakerHeadshotFactory
    @Mock private lateinit var intentFactory:IntentFactory

    private lateinit var subject: SessionDetailActivityImpl

    private lateinit var sessionDetailParam: SessionDetailParam
    private lateinit var fullSession:FullSession

    @Before
    fun setUp() {
        initMocks(this)

        subject = SessionDetailActivityImpl(presenter, speakerHeadshotFactory, intentFactory)

        fullSession = FullSession(
                Id = "123",
                Category = "DevOps",
                SessionStartTime = "2018-01-10T09:15:00",
                SessionEndTime = "2018-01-10T10:15:00",
                SessionType = "session type",
                Title = "title",
                Abstract = "abstract",
                conferenceRooms = listOf(ConferenceRoom(sessionId = "123", name = "banyan"),
                        ConferenceRoom(sessionId = "123", name = "salon e")),
                tags = listOf(Tag(sessionId = "123", name = "tag 1"),
                        Tag(sessionId = "123", name = "tag 2"))
        )
        buildSessionDetailParam()

        whenever(qtn.findViewById<TextView>(R.id.session_title)).thenReturn(title)
        whenever(qtn.findViewById<TextView>(R.id.session_abstract)).thenReturn(abstract)
        whenever(qtn.findViewById<TextView>(R.id.session_category)).thenReturn(category)
        whenever(qtn.findViewById<TextView>(R.id.session_tags)).thenReturn(tags)
        whenever(qtn.findViewById<TextView>(R.id.session_rooms)).thenReturn(rooms)
        whenever(qtn.findViewById<TextView>(R.id.session_time)).thenReturn(sessionTime)
        whenever(qtn.findViewById<TextView>(R.id.session_type)).thenReturn(sessionType)
        whenever(qtn.findViewById<TextView>(R.id.session_date)).thenReturn(sessionDate)
        whenever(qtn.findViewById<LinearLayout>(R.id.speakers_holder)).thenReturn(speakersHolder)
        whenever(qtn.findViewById<LinearLayout>(R.id.speakers_block)).thenReturn(speakersBlock)
        whenever(qtn.intent).thenReturn(intent)
        whenever(intent.getSerializableExtra(SessionDetailActivityImpl.PARAMETER_BLOCK)).thenReturn(sessionDetailParam)
    }

    private fun buildSessionDetailParam(showSpeakers: Boolean = true) {
        sessionDetailParam = SessionDetailParam(fullSession, showSpeakers = showSpeakers)
    }

    @Test
    fun onCreateItSetsTheContentView() {
        subject.onCreate(null, qtn)

        verify(qtn).setContentView(R.layout.activity_session_detail)
    }

    @Test
    fun onCreateItConfiguresTheView() {
        subject.onCreate(null, qtn)

        verify(qtn).getIntent()
        verify(intent).getSerializableExtra(SessionDetailActivityImpl.PARAMETER_BLOCK)

        verify(title).setText(fullSession.Title)
        verify(abstract).setText(fullSession.Abstract)
        verify(category).setText(fullSession.Category)
        verify(sessionType).setText(fullSession.SessionType)
        verify(sessionTime).setText("9:15 AM - 10:15 AM")
        verify(sessionDate).setText("1/10/2018")
        verify(rooms).setText("banyan, salon e")
        verify(tags).setText("tag 1, tag 2")
    }

    @Test
    fun onCreateRequestsTheSpeakersForTheSession() {
        subject.onCreate(null, qtn)

        verify(presenter).retrieveSpeakers(fullSession)
    }
    
    @Test
    fun onCreateDoesNotRequestSpeakersWhenShowSpeakersIsFalse() {
        buildSessionDetailParam(false)
        whenever(intent.getSerializableExtra(SessionDetailActivityImpl.PARAMETER_BLOCK)).thenReturn(sessionDetailParam)

        subject.onCreate(null, qtn)

        verify(speakersBlock).setVisibility(View.GONE)
        verify(presenter, never()).retrieveSpeakers(any())
    }

    @Test
    fun itDisplaysAllSpeakersForTheSession() {
        val speakers = arrayOf(FullSpeaker(), FullSpeaker())
        val speakerHeadshot:SpeakerHeadshot = mock()
        whenever(speakerHeadshotFactory.make(any(), eq(qtn))).thenReturn(speakerHeadshot)

        subject.onCreate(null, qtn)
        subject.displaySpeakers(speakers)

        verify(speakersHolder, times(2)).addView(speakerHeadshot)
        verify(speakerHeadshot, times(2))
                .setLayoutParams(any())
    }

    @Test
    fun onClickOfSpeakerHeadshotItNavigatesToSpeakerDetail() {
        val speaker = FullSpeaker()
        val speakers = arrayOf(speaker)
        val speakerHeadshot:SpeakerHeadshot = mock()
        whenever(speakerHeadshotFactory.make(any(), eq(qtn))).thenReturn(speakerHeadshot)

        subject.onCreate(null, qtn)
        subject.displaySpeakers(speakers)

        val intent = mock<Intent>()
        whenever(intentFactory.make(qtn, SpeakerDetailActivity::class.java)).thenReturn(intent)

        val onClickCaptor = argumentCaptor<View.OnClickListener>()

        verify(speakerHeadshot).setOnClickListener(onClickCaptor.capture())
        onClickCaptor.firstValue.onClick(null)

        val extraCaptor = argumentCaptor<SpeakerDetailParams>()

        verify(intent).putExtra(eq(SpeakerDetailActivityImpl.PARAMETER_BLOCK), extraCaptor.capture())
        val speakerDetailParams = extraCaptor.firstValue
        Assert.assertArrayEquals(speakers, speakerDetailParams.speakers)
        assertEquals(0, speakerDetailParams.index)
    }
}