package com.example.meliexample.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.meliexample.data.Resource
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
abstract class BaseViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val resultUnit = Resource.success(Unit)
    val resultSuccessTrue = Resource.success(true)
    val resultSuccessFalse = Resource.success(false)
    val resultLoading = Resource.loading(false)
    val resultFailure = Result.failure<Exception>(Exception())

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    open fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}
