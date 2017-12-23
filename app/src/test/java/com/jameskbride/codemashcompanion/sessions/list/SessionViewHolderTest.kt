package com.jameskbride.codemashcompanion.sessions.list

import android.view.View
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.DateViewHolder
import com.jameskbride.codemashcompanion.sessions.list.ItemViewHolder
import com.jameskbride.codemashcompanion.sessions.list.TimeViewHolder
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.text.SimpleDateFormat

class SessionViewHolderTest {

    @Mock private lateinit var view: View

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
    private val firstStartTime = "2018-01-10T09:15:00"
    private val firstDate = dateFormatter.parse(firstStartTime)

    @Before
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun timeHeaderViewHolderCanBind() {
        val sessionTime = mock<TextView>()
        whenever(view.findViewById<TextView>(R.id.session_time)).thenReturn(sessionTime)

        val subject = TimeViewHolder(view)
        subject.bind(firstDate)

        val dateFormater = SimpleDateFormat("h:mm a")
        val formattedTimeCaptor = argumentCaptor<String>()
        verify(sessionTime).setText(formattedTimeCaptor.capture())
        val formattedTime = dateFormater.format(firstDate)
        Assert.assertEquals(formattedTime, formattedTimeCaptor.firstValue)
    }

    @Test
    fun sessionItemViewHolderCanBind() {
        val sessionTitle = mock<TextView>()
        val rooms = mock<TextView>()
        val category = mock<TextView>()
        whenever(view.findViewById<TextView>(R.id.session_title)).thenReturn(sessionTitle)
        whenever(view.findViewById<TextView>(R.id.rooms)).thenReturn(rooms)
        whenever(view.findViewById<TextView>(R.id.session_category)).thenReturn(category)
        val subject = ItemViewHolder(view)
        val firstSession =
                FullSession(SessionStartTime = firstStartTime,
                        Category = "some category",
                        conferenceRooms = listOf(
                                ConferenceRoom(sessionId = "id", name = "room 1"),
                                ConferenceRoom(sessionId = "id", name = "room 2"))
                )
        subject.bind(firstSession)

        val sessionTitleCaptor = argumentCaptor<String>()
        verify(sessionTitle).setText(sessionTitleCaptor.capture())
        Assert.assertEquals(firstSession.Title, sessionTitleCaptor.firstValue)

        val roomsCaptor = argumentCaptor<String>()
        verify(rooms).setText(roomsCaptor.capture())
        Assert.assertEquals("room 1, room 2", roomsCaptor.firstValue)

        val categoryCaptor = argumentCaptor<String>()
        verify(category).setText(categoryCaptor.capture())
        Assert.assertEquals("some category", categoryCaptor.firstValue)
    }

    @Test
    fun dateItemViewHolderCanBind() {
        val dateText = mock<TextView>()
        whenever(view.findViewById<TextView>(R.id.session_date)).thenReturn(dateText)

        val subject = DateViewHolder(view)
        val day = "Day 1"
        subject.bind(day)

        val dayCaptor = argumentCaptor<String>()
        verify(dateText).setText(dayCaptor.capture())
        Assert.assertEquals(day, dayCaptor.firstValue)
    }
}