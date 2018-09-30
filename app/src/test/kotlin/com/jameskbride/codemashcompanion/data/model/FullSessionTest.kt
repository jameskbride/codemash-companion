package com.jameskbride.codemashcompanion.data.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FullSessionTest {

    @Test
    fun itIsBookmarkedIfItHasBookmarks() {
        val fullSession = FullSession(
                bookmarks = listOf(
                        Bookmark()
                )
        )

        assertTrue(fullSession.isBookmarked)
    }

    @Test
    fun itIsNotBookmarkedIfItHasNoBookmarks() {
        val fullSession = FullSession()

        assertFalse(fullSession.isBookmarked)
    }
}