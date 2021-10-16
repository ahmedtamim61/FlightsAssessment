package com.example.data.repository

import com.example.network.datasource.FlightsListDataSource
import com.example.network.model.Flight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FlightsListRepository @Inject constructor(private val flightsListDataSource: FlightsListDataSource) {

    suspend fun fetchFlights() : Flow<List<Flight>> = flow {
        emit(flightsListDataSource.fetchFlightsList())
    }
}