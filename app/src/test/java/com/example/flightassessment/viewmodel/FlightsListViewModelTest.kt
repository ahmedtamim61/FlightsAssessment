package com.example.flightassessment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.usecase.GetFlightsUseCase
import com.example.flightassessment.ui.model.FlightUiModel
import com.example.network.model.Flight
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class FlightsListViewModelTest {

    private lateinit var viewModel: FlightsListViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: GetFlightsUseCase

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = FlightsListViewModel(useCase)
    }

    private val flightsSuccessObserver = Observer<List<FlightUiModel>> {
        assertEquals("Indigo", it[0].airlineName)
    }

    private val flightsFailureObserver = Observer<String> {
        assertEquals("Error", it)
    }

    @Test
    fun `test fetch flights success`() {
        runBlocking {
            val response = listOf(getDummyFlight(), getDummyFlight(), getDummyFlight())
            val flow = flow { emit(response) }
            coEvery { useCase.getFlights() } returns flow
            viewModel.flightsListLiveData.observeForever(flightsSuccessObserver)
            viewModel.fetchFlightsList()
        }
    }

    @Test
    fun `test fetch flights failure`() {
        runBlocking {
            coEvery { useCase.getFlights() } throws Exception("Error")
            viewModel.errorLiveData.observeForever(flightsFailureObserver)
            viewModel.fetchFlightsList()
        }
    }

    @After
    fun tearDown() {
        viewModel.flightsListLiveData.removeObserver(flightsSuccessObserver)
        viewModel.errorLiveData.removeObserver(flightsFailureObserver)
    }

    private fun getDummyFlight() = Flight (
        "Indigo",
        "2021-09-29 17:30",
        "2021-09-29 15:35",
        "BOM",
        "https://www.goindigo.in/content/dam/indigov2/6e-website/thmbnail.jpg",
        "Del",
        "1200")
}