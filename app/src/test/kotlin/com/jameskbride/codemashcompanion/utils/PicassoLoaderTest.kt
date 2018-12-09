package com.jameskbride.codemashcompanion.utils

import android.content.Context
import android.widget.ImageView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.utils.test.buildDefaultFullSpeakers
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.squareup.picasso.Transformation
import org.junit.Before
import org.junit.Test

class PicassoLoaderTest {

    private lateinit var picassoWrapper:PicassoWrapper
    private lateinit var context:Context
    private lateinit var logWrapper:LogWrapper
    private lateinit var speakerImage:ImageView

    private lateinit var picassoLoader:PicassoLoader

    @Before
    fun setUp() {
        picassoWrapper = mock()
        context = mock()
        logWrapper = mock()
        speakerImage = mock()

        whenever(speakerImage.context).thenReturn(context)

        picassoLoader = PicassoLoader(picassoWrapper, logWrapper)
    }

    @Test
    fun bindLoadsTheSpeakerDataIntoTheView() {
        val speaker = buildDefaultFullSpeakers()[0]
        setupBasicBuilderSteps(speaker)

        picassoLoader.load(speaker, speakerImage)

        verify(picassoWrapper).into(speakerImage)
    }

    @Test
    fun itAddsATransformationWhenOneIsProvided() {
        val speaker = buildDefaultFullSpeakers()[0]
        setupBasicBuilderSteps(speaker)

        val transformation = CircleTransform()
        picassoLoader.load(speaker, speakerImage, transformation = transformation)

        verify(picassoWrapper).into(speakerImage)
        verify(picassoWrapper).transform(transformation)

    }

    private fun setupBasicBuilderSteps(speaker: FullSpeaker) {
        whenever(picassoWrapper.with(context)).thenReturn(picassoWrapper)
        whenever(picassoWrapper.load("${speaker.GravatarUrl}")).thenReturn(picassoWrapper)
        whenever(picassoWrapper.placeholder(R.drawable.ic_person)).thenReturn(picassoWrapper)
        whenever(picassoWrapper.resize(500, 500)).thenReturn(picassoWrapper)
        whenever(picassoWrapper.centerCrop()).thenReturn(picassoWrapper)
        whenever(picassoWrapper.transform(any<Transformation>())).thenReturn(picassoWrapper)
    }
}