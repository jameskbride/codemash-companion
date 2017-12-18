package com.jameskbride.codemashcompanion.sessions.detail

import android.content.Intent
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Tag
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailActivityImpl.SessionDetailParam
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
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

    private lateinit var subject: SessionDetailActivityImpl

    private lateinit var sessionDetailParam: SessionDetailParam
    private lateinit var fullSession:FullSession

    @Before
    fun setUp() {
        initMocks(this)

        subject = SessionDetailActivityImpl()

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
        sessionDetailParam = SessionDetailParam(fullSession)

        whenever(qtn.findViewById<TextView>(R.id.session_title)).thenReturn(title)
        whenever(qtn.findViewById<TextView>(R.id.session_abstract)).thenReturn(abstract)
        whenever(qtn.findViewById<TextView>(R.id.session_category)).thenReturn(category)
        whenever(qtn.findViewById<TextView>(R.id.session_tags)).thenReturn(tags)
        whenever(qtn.findViewById<TextView>(R.id.session_rooms)).thenReturn(rooms)
        whenever(qtn.findViewById<TextView>(R.id.session_time)).thenReturn(sessionTime)
        whenever(qtn.findViewById<TextView>(R.id.session_type)).thenReturn(sessionType)
        whenever(qtn.findViewById<TextView>(R.id.session_date)).thenReturn(sessionDate)
        whenever(qtn.intent).thenReturn(intent)
        whenever(intent.getSerializableExtra(SessionDetailActivityImpl.PARAMETER_BLOCK)).thenReturn(sessionDetailParam)
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
}