package com.example.flightassessment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flightassessment.databinding.ListItemFlightBinding
import com.example.flightassessment.ui.model.FlightUiModel

class FlightsListAdapter :
    ListAdapter<FlightUiModel, FlightsListAdapter.FlightViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : FlightViewHolder {
        val binding = ListItemFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FlightViewHolder(private val binding : ListItemFlightBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(flightUIModel: FlightUiModel?) {
            binding.flightUIModel = flightUIModel
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FlightUiModel>() {
        override fun areItemsTheSame(oldItem: FlightUiModel, newItem: FlightUiModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FlightUiModel, newItem: FlightUiModel): Boolean {
            return oldItem == newItem
        }
    }
}