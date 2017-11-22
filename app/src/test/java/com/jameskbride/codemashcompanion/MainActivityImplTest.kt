package com.jameskbride.codemashcompanion

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MainActivityImplTest {

    @Test
    fun itCanDelegateToTheMainActivity() {
        val base = mock(MainActivity::class.java)
        val mainActivityImpl = MainActivityImpl()

        mainActivityImpl.onCreate(null, base)

        verify(base).setContentView(R.layout.activity_main)
    }
}
