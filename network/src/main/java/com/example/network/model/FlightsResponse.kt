package com.example.network.model

data class FlightsResponse(
    val flights : List<Flight>
)

data class Flight(
    val Airline: String,
    val Arrival: String,
    val Departure: String,
    val destination: String,
    val logo: String,
    val source: String,
    val ticketPrice: String
)
