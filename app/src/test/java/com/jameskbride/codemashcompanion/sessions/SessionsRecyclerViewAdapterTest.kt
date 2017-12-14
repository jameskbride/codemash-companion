package com.jameskbride.codemashcompanion.sessions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Session
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.text.SimpleDateFormat
import java.util.*

class SessionsRecyclerViewAdapterTest {

    @Mock private lateinit var qtn: SessionsRecyclerViewAdapter
    @Mock private lateinit var layoutInflaterFactory: LayoutInflaterFactory
    @Mock private lateinit var container:ViewGroup
    @Mock private lateinit var view: View
    @Mock private lateinit var context:Context

    private lateinit var subject: SessionsRecyclerViewAdapterImpl

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")

    @Before
    fun setUp() {
        initMocks(this)
        subject = SessionsRecyclerViewAdapterImpl(layoutInflaterFactory)

        whenever(container.context).thenReturn(context)

        buildDefaultSessionData()
    }

    private lateinit var sessionData: SessionData
    private val firstStartTime = "2018-01-11T09:15:00"
    private val secondStartTime = "2018-01-11T10:15:00"
    private val firstDate = dateFormatter.parse(firstStartTime)
    private val secondDate = dateFormatter.parse(secondStartTime)
    private val firstSession = Session(SessionStartTime = firstStartTime)
    private val secondSession = Session(SessionStartTime = secondStartTime)
    private val thirdSession = Session(SessionStartTime = secondStartTime)

    private fun buildDefaultSessionData() {
        var sessions = arrayOf(
                firstSession,
                secondSession,
                thirdSession
        )

        sessionData = SessionData(sessions)
    }

    @Test
    fun itNotifiesOfChangesWhenTheSessionsAreSet() {
        val sessions = SessionData()
        subject.setSessions(sessions, qtn)

        verify(qtn).notifyDataSetChanged()
    }

    @Test
    fun itConvertsSessionsToListItems() {
        subject.setSessions(sessionData, qtn)

        val result = subject.sessionsList
        assertEquals(5, result.size)

        val firstHeader:HeaderListItem = result[0] as HeaderListItem
        assertEquals(firstDate, firstHeader.sessionTime)

        val firstItem:SessionListItem = result[1] as SessionListItem
        assertEquals(firstSession, firstItem.session)

        val secondHeader:HeaderListItem = result[2] as HeaderListItem
        assertEquals(secondDate, secondHeader.sessionTime)

        val secondItem:SessionListItem = result[3] as SessionListItem
        assertEquals(secondSession, secondItem.session)

        val thirdItem:SessionListItem = result[4] as SessionListItem
        assertEquals(thirdSession, thirdItem.session)
    }

    @Test
    fun itGetTheSizeIncludingHeaders() {
        subject.setSessions(sessionData, qtn)

        assertEquals(5, subject.getItemCount())
    }

    @Test
    fun itGetsTheItemType() {
        subject.setSessions(sessionData, qtn)

        assertEquals(ListItem.HEADER_TYPE, subject.getItemViewType(0))
        assertEquals(ListItem.ITEM_TYPE, subject.getItemViewType(1))
        assertEquals(ListItem.HEADER_TYPE, subject.getItemViewType(2))
        assertEquals(ListItem.ITEM_TYPE, subject.getItemViewType(3))
        assertEquals(ListItem.ITEM_TYPE, subject.getItemViewType(4))
    }

    @Test
    fun itCreatesAHeaderSessionViewHolderForHeaderListItems() {
        subject.setSessions(sessionData, qtn)
        whenever(layoutInflaterFactory.inflate(context, R.layout.sessions_header, container)).thenReturn(view)

        val sessionViewHolder = subject.onCreateViewHolder(container, ListItem.HEADER_TYPE)

        verify(layoutInflaterFactory).inflate(context, R.layout.sessions_header, container)
        assertTrue(sessionViewHolder is HeaderViewHolder)
    }

    @Test
    fun itCreatesAnItemViewHolderForListItems() {
        subject.setSessions(sessionData, qtn)
        whenever(layoutInflaterFactory.inflate(context, R.layout.sessions_item, container)).thenReturn(view)

        val sessionViewHolder = subject.onCreateViewHolder(container, ListItem.ITEM_TYPE)

        verify(layoutInflaterFactory).inflate(context, R.layout.sessions_item, container)
        assertTrue(sessionViewHolder is ItemViewHolder)
    }

    @Test
    fun itBindsHeaderViewHolders() {
        subject.setSessions(sessionData, qtn)

        val headerViewHolder = mock<HeaderViewHolder>()

        subject.onBindViewHolder(headerViewHolder, 0)

        verify(headerViewHolder).bind(firstDate)
    }
//
//    @Test
//    fun itBindsItemViewHolders() {
//        subject.setSessions(sessionData, qtn)
//
//        val itemViewHolder = mock<ItemViewHolder>()
//
//        subject.onBindViewHolder(itemViewHolder, 1)
//
//        verify(itemViewHolder).bind(sessions[firstDate]!![0])
//    }

    @Test
    fun headerViewHolderCanBind() {
        val sessionTime = mock<TextView>()
        whenever(view.findViewById<TextView>(R.id.session_time)).thenReturn(sessionTime)

        val subject = HeaderViewHolder(view)

        subject.bind(firstDate)
        val dateFormater = SimpleDateFormat("h:mm a")
        val formattedTimeCaptor = argumentCaptor<String>()
        verify(sessionTime).setText(formattedTimeCaptor.capture())
        val formattedTime = dateFormater.format(firstDate)
        assertEquals(formattedTime, formattedTimeCaptor.firstValue)
    }

    @Test
    fun itemViewHolderCanBind() {
        val sessionTitle = mock<TextView>()
        whenever(view.findViewById<TextView>(R.id.session_title)).thenReturn(sessionTitle)

        val subject = ItemViewHolder(view)

        subject.bind(firstSession)
        val sessionTitleCaptor = argumentCaptor<String>()
        verify(sessionTitle).setText(sessionTitleCaptor.capture())
        assertEquals(firstSession.Title, sessionTitleCaptor.firstValue)
    }
}