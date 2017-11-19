package com.jameskbride.codemashcompanion

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ExampleUnitTest {

    @Test
    fun mockingWorks() {
        val testClass1 = mock(TestClass1::class.java)
        val testClass2 = TestClass2(testClass1)

        testClass2.doSomething()

        verify(testClass1).doSomething()
    }
}

class TestClass1 {
    fun doSomething() {}
}

class TestClass2(private val testClass1: TestClass1) {
    fun doSomething() {
        testClass1.doSomething()
    }
}
