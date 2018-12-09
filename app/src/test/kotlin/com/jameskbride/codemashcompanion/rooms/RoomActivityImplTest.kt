package com.jameskbride.codemashcompanion.rooms

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import android.widget.ImageView
import com.github.chrisbanes.photoview.PhotoView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.error.PARAMETER_BLOCK
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
    @Mock private lateinit var intent:Intent
    @Mock private lateinit var mapView:PhotoView

    private lateinit var subject:RoomActivityImpl

    @DrawableRes val map:Int = R.drawable.full

    @Before
    fun setUp() {
        initMocks(this)

        subject = RoomActivityImpl()

        whenever(qtn.findViewById<Toolbar>(R.id.toolbar)).thenReturn(toolbar)
        whenever(qtn.supportActionBar).thenReturn(actionBar)
        whenever(qtn.findViewById<ImageView>(R.id.map_view)).thenReturn(mapView)
        whenever(qtn.intent).thenReturn(intent)

        val roomParams = RoomParams(room = map)
        whenever(intent.getSerializableExtra(PARAMETER_BLOCK)).thenReturn(roomParams)

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

    @Test
    fun onCreateSetsTheMapFromTheParams() {
        subject.onCreate(null, qtn)

        verify(mapView).setImageResource(map)
    }
}