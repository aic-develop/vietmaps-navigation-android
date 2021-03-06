package com.mapbox.navigation.route.onboard

import com.mapbox.navigation.utils.thread.ThreadController
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestRule {
    val coroutineDispatcher = TestCoroutineDispatcher()
    val coroutineScope = TestCoroutineScope(coroutineDispatcher)

    override fun apply(base: Statement, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(coroutineDispatcher)

            mockkObject(ThreadController)
            every { ThreadController.IODispatcher } returns coroutineDispatcher

            base.evaluate()

            unmockkObject(ThreadController)

            Dispatchers.resetMain() // Restore original main dispatcher
            coroutineScope.cleanupTestCoroutines()
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
            coroutineScope.runBlockingTest { block() }
}

@ExperimentalCoroutinesApi
fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) =
        this.coroutineDispatcher.runBlockingTest {
            block()
        }
