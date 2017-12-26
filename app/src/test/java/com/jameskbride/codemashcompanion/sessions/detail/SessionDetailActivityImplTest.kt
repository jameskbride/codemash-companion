package com.jameskbride.codemashcompanion.sessions.detail

import android.content.Intent
import android.support.annotation.IdRes
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.*
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl.Companion.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.rooms.RoomActivity
import com.jameskbride.codemashcompanion.rooms.RoomParams
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivity
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl
import com.jameskbride.codemashcompanion.speakers.detail.SpeakersParams
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.Toaster
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
    @Mock private lateinit var toolbar:Toolbar
    @Mock private lateinit var speakersHolder:LinearLayout
    @Mock private lateinit var speakersBlock:LinearLayout
    @Mock private lateinit var actionBar:ActionBar
    @Mock private lateinit var addBookmarkFAB:FloatingActionButton
    @Mock private lateinit var removeBookmarkFAB:FloatingActionButton
    @Mock private lateinit var presenter:SessionDetailActivityPresenter
    @Mock private lateinit var speakerHeadshotFactory:SpeakerHeadshotFactory
    @Mock private lateinit var intentFactory:IntentFactory
    @Mock private lateinit var toaster:Toaster
    @Mock private lateinit var speakerHeadshot:SpeakerHeadshot
    @Mock private lateinit var roomsBlock:LinearLayout

    private lateinit var subject: SessionDetailActivityImpl

    private lateinit var sessionDetailParam: SessionDetailParam
    private lateinit var fullSession:FullSession

    @Before
    fun setUp() {
        initMocks(this)

        subject = SessionDetailActivityImpl(presenter, speakerHeadshotFactory, intentFactory, toaster)

        fullSession = buildDefaultFullSession()
        buildSessionDetailParam(fullSession)

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
        whenever(qtn.findViewById<Toolbar>(R.id.toolbar)).thenReturn(toolbar)
        whenever(qtn.findViewById<FloatingActionButton>(R.id.add_bookmark_fab)).thenReturn(addBookmarkFAB)
        whenever(qtn.findViewById<FloatingActionButton>(R.id.remove_bookmark_fab)).thenReturn(removeBookmarkFAB)
        whenever(qtn.findViewById<LinearLayout>(R.id.rooms_block)).thenReturn(roomsBlock)
        whenever(qtn.intent).thenReturn(intent)
        whenever(intent.getSerializableExtra(PARAMETER_BLOCK)).thenReturn(sessionDetailParam)

        whenever(speakerHeadshotFactory.make(any(), eq(qtn))).thenReturn(speakerHeadshot)
    }

    @Test
    fun onCreateItSetsTheContentView() {
        subject.onCreate(null, qtn)

        verify(qtn).setContentView(R.layout.activity_session_detail)
    }

    @Test
    fun onCreateSetsTheTitle() {
        subject.onCreate(null, qtn)

        verify(toolbar).setTitle(R.string.session_detail)
    }

    @Test
    fun onCreateRetrievesTheSessionDetail() {
        subject.onCreate(null, qtn)

        verify(qtn).getIntent()
        verify(intent).getSerializableExtra(PARAMETER_BLOCK)
    }

    @Test
    fun itCanConfigureTheViewForASession() {
        initSessionDetailActivity()

        subject.configureForSession(fullSession)

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
    fun onCreateConfiguresTheUpNavigation() {
        whenever(qtn.supportActionBar).thenReturn(actionBar)
        subject.onCreate(null, qtn)

        verify(qtn).setSupportActionBar(toolbar)
        verify(actionBar).setDisplayHomeAsUpEnabled(true)
    }

    @Test
    fun configureForSessionRequestsTheSpeakersForTheSession() {
        subject.onCreate(null, qtn)
        subject.configureForSession(fullSession)

        verify(presenter).retrieveSpeakers(fullSession.Id)
    }

    @Test
    fun configureForSessionDoesNotRequestSpeakersWhenShowSpeakersIsFalse() {
        buildSessionDetailParam(buildDefaultFullSession(), false)
        whenever(intent.getSerializableExtra(PARAMETER_BLOCK)).thenReturn(sessionDetailParam)
        subject.onCreate(null, qtn)

        subject.configureForSession(fullSession)

        verify(speakersBlock).setVisibility(View.GONE)
        verify(presenter, never()).retrieveSpeakers(any())
    }

    @Test
    fun whenTheSessionHasBeenBookmarkedTheRemoveBookmarkFABIsVisible() {
        fullSession = buildDefaultFullSession()
        fullSession.bookmarks = listOf(
                Bookmark(sessionId = fullSession.Id)
        )

        buildSessionDetailParam(fullSession)
        whenever(intent.getSerializableExtra(PARAMETER_BLOCK)).thenReturn(sessionDetailParam)

        subject.onCreate(null, qtn)

        subject.configureForSession(fullSession)

        verify(removeBookmarkFAB).setVisibility(View.VISIBLE)
        verify(addBookmarkFAB).setVisibility(View.GONE)
    }

    @Test
    fun whenTheSessionHasBeenNotBookmarkedTheAddFABIsVisible() {
        initSessionDetailActivity()

        subject.configureForSession(fullSession)

        verify(removeBookmarkFAB).setVisibility(View.GONE)
        verify(addBookmarkFAB).setVisibility(View.VISIBLE)
    }

    @Test
    fun addBookmarkCanAddABookmark() {
        initSessionDetailActivity()

        subject.configureForSession(fullSession)

        val onClickListenerCaptor = argumentCaptor<View.OnClickListener>()
        verify(addBookmarkFAB).setOnClickListener(onClickListenerCaptor.capture())
        onClickListenerCaptor.firstValue.onClick(null)

        verify(presenter).addBookmark(fullSession)
    }

    @Test
    fun removeBookmarkCanRemoveABookmark() {
        initSessionDetailActivity()

        subject.configureForSession(fullSession)

        val onClickListenerCaptor = argumentCaptor<View.OnClickListener>()
        verify(removeBookmarkFAB).setOnClickListener(onClickListenerCaptor.capture())
        onClickListenerCaptor.firstValue.onClick(null)

        verify(presenter).removeBookmark(fullSession)
    }

    @Test
    fun itDisplaysAllSpeakersForTheSession() {
        val speakers = arrayOf(FullSpeaker(), FullSpeaker())

        subject.onCreate(null, qtn)
        subject.displaySpeakers(speakers)

        verify(speakersHolder).removeAllViews()
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

        val extraCaptor = argumentCaptor<SpeakersParams>()

        verify(intent).putExtra(eq(PARAMETER_BLOCK), extraCaptor.capture())
        val speakerDetailParams = extraCaptor.firstValue
        Assert.assertArrayEquals(speakers, speakerDetailParams.speakers)
        assertEquals(0, speakerDetailParams.index)
    }

    @Test
    fun onClickOfRoomsNavigatesToTheRoomsView() {
        subject.onCreate(null, qtn)
        subject.configureForSession(fullSession)

        val intent = mock<Intent>()
        whenever(intentFactory.make(qtn, RoomActivity::class.java)).thenReturn(intent)

        val onClickCaptor = argumentCaptor<View.OnClickListener>()
        verify(roomsBlock).setOnClickListener(onClickCaptor.capture())
        onClickCaptor.firstValue.onClick(null)

        val roomParamsCaptor = argumentCaptor<RoomParams>()
        verify(intent).putExtra(eq(PARAMETER_BLOCK), roomParamsCaptor.capture())
        assertTrue(roomParamsCaptor.firstValue.rooms.containsAll(fullSession.conferenceRooms))
        verify(qtn).startActivity(intent)
    }

    @Test
    fun itCanDisplayAnErrorMessage() {
        whenever(toaster.makeText(qtn, R.string.unexpected_error, Toast.LENGTH_SHORT)).thenReturn(toaster)
        subject.onCreate(null, qtn)

        subject.displayErrorMessage(R.string.unexpected_error)

        verify(toaster).show()
    }

    @Test
    fun itCanNavigateUpWhenHomeIsSelected() {
        val menuItem = mock<MenuItem>()
        whenever(menuItem.itemId).thenReturn(android.R.id.home)

        val result = subject.onOptionsItemSelected(menuItem, qtn)

        assertTrue(result)
        verify(qtn).onBackPressed()
    }

    @Test
    fun itNavigatesAccordingToSuperWhenHomeIsNotSelected() {
        val menuItem = mock<MenuItem>()
        @IdRes val someInt = 42
        whenever(menuItem.itemId).thenReturn(someInt)

        subject.onOptionsItemSelected(menuItem, qtn)

        verify(qtn).callSuperOnOptionsItemSelected(menuItem)
    }

    @Test
    fun onResumeItOpensThePresenter() {
        initSessionDetailActivity()
        subject.onResume(qtn)

        verify(presenter).open()
    }

    @Test
    fun onResumeRetrievesTheSession() {
        initSessionDetailActivity()

        subject.onResume(qtn)

        verify(presenter).retrieveSession(sessionDetailParam.sessionId)
    }

    private fun initSessionDetailActivity() {
        buildSessionDetailParam(fullSession)
        whenever(intent.getSerializableExtra(PARAMETER_BLOCK)).thenReturn(sessionDetailParam)
        subject.onCreate(null, qtn)
    }

    @Test
    fun onPauseItClosesThePresenter() {
        subject.onPause(qtn)

        verify(presenter).close()
    }

    private fun buildDefaultFullSession(): FullSession {
        return FullSession(
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
    }

    private fun buildSessionDetailParam(fullSession: FullSession, showSpeakers: Boolean = true) {
        sessionDetailParam = SessionDetailParam(showSpeakers = showSpeakers, sessionId = fullSession.Id)
    }
}