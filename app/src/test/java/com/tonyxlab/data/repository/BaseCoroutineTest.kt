package com.tonyxlab.data.repository

import kotlinx.coroutines.test.TestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseCoroutineTest {

    @get:Rule
    val testCoroutineDispatcher = TestDispatcherRule()

    @Before
    open fun onCreate(){}

    @After
    open fun onDestroy(){}
}