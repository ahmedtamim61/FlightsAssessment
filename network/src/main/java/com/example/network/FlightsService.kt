package com.example.network

import com.example.network.model.FlightsResponse
import retrofit2.http.GET

interface FlightsService {

    @GET("/getAllFlights")
    suspend fun getAllFlights() : FlightsResponse
}