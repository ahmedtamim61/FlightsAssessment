package com.example.flightassessment.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.usecase.GetFlightsUseCase
import com.example.flightassessment.R
import com.example.flightassessment.ui.model.FlightUiModel
import com.example.flightassessment.utils.DateTimeUtils
import com.example.network.model.Flight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onErrorReturn
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FlightsListViewModel @Inject constructor(private val flightsUseCase : GetFlightsUseCase) : ViewModel(),
    View.OnClickListener {

    private val flightsListMutableLiveData = MutableLiveData<List<FlightUiModel>>()
    val flightsListLiveData : LiveData<List<FlightUiModel>> = flightsListMutableLiveData

    private val progressMutableLiveData = MutableLiveData<Boolean>()
    val progressLiveData : LiveData<Boolean> = progressMutableLiveData

    private val errorMutableLiveData = MutableLiveData<String>()
    val errorLiveData : LiveData<String> = errorMutableLiveData

    fun fetchFlightsList() {
        viewModelScope.launch {
            progressMutableLiveData.value = true
            try {
                flightsUseCase.getFlights()
                    .flowOn(Dispatchers.Main.immediate)
                    .collect { flights ->
                        progressMutableLiveData.value = false
                        flightsListMutableLiveData.value = flights.map {
                            getFlightUiModel(it)
                        }
                    }
            } catch (e : Exception) {
                progressMutableLiveData.value = false
                errorMutableLiveData.value = e.localizedMessage
            }
        }
    }

    private fun getFlightUiModel(flight : Flight) =
        FlightUiModel(
            flight.Airline,
            flight.logo,
            flight.source,
            DateTimeUtils.getTime(flight.Departure),
            flight.destination,
            DateTimeUtils.getTime(flight.Arrival),
            DateTimeUtils.convertMillisToHours(
                DateTimeUtils.getDateInMillis(flight.Arrival) - DateTimeUtils.getDateInMillis(flight.Departure)
            ),
            flight.ticketPrice
        )

    override fun onClick(view: View) {
        when (view.tag) {
            view.context.getString(R.string.sort_by_price) -> {
                flightsListMutableLiveData.value = flightsListMutableLiveData.value?.sortedBy {
                    it.price
                }
            }
            view.context.getString(R.string.sort_by_departure) -> {
                flightsListMutableLiveData.value = flightsListMutableLiveData.value?.sortedBy {
                    it.departureTime
                }
            }
        }
    }
}