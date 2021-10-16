package com.example.data.usecase

import com.example.data.repository.FlightsListRepository
import com.example.network.model.Flight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFlightsUseCase @Inject constructor(private val repository: FlightsListRepository) {

    suspend fun getFlights() : Flow<List<Flight>> =
        repository.fetchFlights().flowOn(Dispatchers.IO)
}