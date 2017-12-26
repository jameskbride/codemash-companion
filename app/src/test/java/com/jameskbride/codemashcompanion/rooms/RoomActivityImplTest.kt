package com.jameskbride.codemashcompanion.rooms

import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class RoomActivityImplTest {

    @Mock private lateinit var qtn:RoomActivity
    @Mock private lateinit var toolbar:Toolbar
    @Mock private lateinit var actionBar:ActionBar

    private lateinit var subject:RoomActivityImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = RoomActivityImpl()

        whenever(qtn.findViewById<Toolbar>(R.id.toolbar)).thenReturn(toolbar)
        whenever(qtn.supportActionBar).thenReturn(actionBar)
    }

    @Test
    fun onCreateSetsTheContentView() {
        subject.onCreate(null, qtn)

        verify(qtn).setContentView(R.layout.activity_room)
    }

    @Test
    fun onCreateSetsTheActionBar() {
        subject.onCreate(null, qtn)

        verify(qtn).setSupportActionBar(toolbar)
        verify(actionBar).setDisplayHomeAsUpEnabled(true)
    }

    @Test
    fun onCreateSetsTheTitle() {
        subject.onCreate(null, qtn)

        verify(toolbar).setTitle(R.string.map)
    }
}