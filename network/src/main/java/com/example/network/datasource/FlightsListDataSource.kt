package com.example.network.datasource

import com.example.network.FlightsService
import com.example.network.model.Flight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FlightsListDataSource @Inject constructor(private val apiService : FlightsService) {

    suspend fun fetchFlightsList() : List<Flight> =
        apiService.getAllFlights().flights
}