package com.jameskbride.codemashcompanion.sessions.list

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
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

class SessionsRecyclerViewAdapterImplTest {

    @Mock private lateinit var qtn: SessionsRecyclerViewAdapter
    @Mock private lateinit var layoutInflaterFactory: LayoutInflaterFactory
    @Mock private lateinit var container:ViewGroup
    @Mock private lateinit var view: View
    @Mock private lateinit var context:Context
    @Mock private lateinit var sessionsFragmentPresenter: SessionsFragmentPresenter

    private lateinit var subject: SessionsRecyclerViewAdapterImpl

    private val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)

    @Before
    fun setUp() {
        initMocks(this)
        subject = SessionsRecyclerViewAdapterImpl(sessionsFragmentPresenter, layoutInflaterFactory)

        whenever(container.context).thenReturn(context)

        buildDefaultSessionData()
    }

    private lateinit var sessionData: SessionData
    private val firstStartTime = "2018-01-10T09:15:00"
    private val firstDate = dateFormatter.parse(firstStartTime)
    private val firstSession = FullSession(SessionStartTime = firstStartTime)
    private val secondStartTime = "2018-01-11T10:15:01"
    private val secondDate = dateFormatter.parse(secondStartTime)
    private val secondSession = FullSession(SessionStartTime = secondStartTime)
    private val thirdSession = FullSession(SessionStartTime = secondStartTime)

    private fun buildDefaultSessionData() {
        var sessions:Array<FullSession> = arrayOf(
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
        assertEquals(7, result.size)

        val firstDateHeader: DateHeaderListItem = result[0] as DateHeaderListItem
        assertEquals("Day 1", firstDateHeader.text)

        val firstTimeHeader: TimeHeaderListItem = result[1] as TimeHeaderListItem
        assertEquals(firstDate, firstTimeHeader.sessionTime)

        val firstItem: SessionListItem = result[2] as SessionListItem
        assertEquals(firstSession, firstItem.session)

        val secondDateHeader: DateHeaderListItem = result[3] as DateHeaderListItem
        assertEquals("Day 2", secondDateHeader.text)

        val secondTimeHeader: TimeHeaderListItem = result[4] as TimeHeaderListItem
        assertEquals(secondDate, secondTimeHeader.sessionTime)

        val secondItem: SessionListItem = result[5] as SessionListItem
        assertEquals(secondSession, secondItem.session)

        val thirdItem: SessionListItem = result[6] as SessionListItem
        assertEquals(thirdSession, thirdItem.session)
    }

    @Test
    fun itGetTheSizeIncludingHeaders() {
        subject.setSessions(sessionData, qtn)

        assertEquals(7, subject.getItemCount())
    }

    @Test
    fun itGetsTheItemType() {
        subject.setSessions(sessionData, qtn)

        assertEquals(ListItem.DATE_HEADER_TYPE, subject.getItemViewType(0))
        assertEquals(ListItem.TIME_HEADER_TYPE, subject.getItemViewType(1))
        assertEquals(ListItem.SESSION_ITEM_TYPE, subject.getItemViewType(2))
        assertEquals(ListItem.DATE_HEADER_TYPE, subject.getItemViewType(3))
        assertEquals(ListItem.TIME_HEADER_TYPE, subject.getItemViewType(4))
        assertEquals(ListItem.SESSION_ITEM_TYPE, subject.getItemViewType(5))
        assertEquals(ListItem.SESSION_ITEM_TYPE, subject.getItemViewType(6))
    }

    @Test
    fun itCreatesAHeaderSessionViewHolderForDateHeaderListItems() {
        subject.setSessions(sessionData, qtn)
        whenever(layoutInflaterFactory.inflate(context, R.layout.sessions_date_header, container)).thenReturn(view)

        val sessionViewHolder = subject.onCreateViewHolder(container, ListItem.DATE_HEADER_TYPE)

        verify(layoutInflaterFactory).inflate(context, R.layout.sessions_date_header, container)
        assertTrue(sessionViewHolder is DateViewHolder)
    }

    @Test
    fun itCreatesAHeaderSessionViewHolderForTimeHeaderListItems() {
        subject.setSessions(sessionData, qtn)
        whenever(layoutInflaterFactory.inflate(context, R.layout.sessions_time_header, container)).thenReturn(view)

        val sessionViewHolder = subject.onCreateViewHolder(container, ListItem.TIME_HEADER_TYPE)

        verify(layoutInflaterFactory).inflate(context, R.layout.sessions_time_header, container)
        assertTrue(sessionViewHolder is TimeViewHolder)
    }

    @Test
    fun itCreatesAnItemViewHolderForSessionListItems() {
        subject.setSessions(sessionData, qtn)
        whenever(layoutInflaterFactory.inflate(context, R.layout.sessions_item, container)).thenReturn(view)

        val sessionViewHolder = subject.onCreateViewHolder(container, ListItem.SESSION_ITEM_TYPE)

        verify(layoutInflaterFactory).inflate(context, R.layout.sessions_item, container)
        assertTrue(sessionViewHolder is ItemViewHolder)
    }

    @Test
    fun itBindsDateHeaderViewHolders() {
        subject.setSessions(sessionData, qtn)

        val dateHeaderViewHolder = mock<DateViewHolder>()

        subject.onBindViewHolder(dateHeaderViewHolder, 0)

        verify(dateHeaderViewHolder).bind("Day 1")
    }

    @Test
    fun itBindsTimeHeaderViewHolders() {
        subject.setSessions(sessionData, qtn)

        val headerViewHolder = mock<TimeViewHolder>()

        subject.onBindViewHolder(headerViewHolder, 1)

        verify(headerViewHolder).bind(firstDate)
    }

    @Test
    fun itBindsSessionItemViewHolders() {

        subject.setSessions(sessionData, qtn)

        val itemViewHolder = mock<ItemViewHolder>()
        whenever(itemViewHolder.view).thenReturn(view)

        subject.onBindViewHolder(itemViewHolder, 2)
        verify(itemViewHolder).bind(firstSession)

        val onClickCaptor = argumentCaptor<View.OnClickListener>()
        verify(view).setOnClickListener(onClickCaptor.capture())
        onClickCaptor.firstValue.onClick(null)
        verify(sessionsFragmentPresenter).navigateToSessionDetail(firstSession)

    }
}