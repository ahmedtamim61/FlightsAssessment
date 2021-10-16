package com.example.flightassessment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.flightassessment.R
import com.example.flightassessment.databinding.FragmentFlightsListBinding
import com.example.flightassessment.viewmodel.FlightsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightsListFragment : BaseFragment() {

    private val viewModel : FlightsListViewModel by viewModels()
    private lateinit var binding : FragmentFlightsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFlightsListBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.recyclerView.adapter = FlightsListAdapter()
        viewModel.run {
            progressLiveData.observe(viewLifecycleOwner, {
                it?.let { binding.progressBar.isVisible = it }
            })
            flightsListLiveData.observe(viewLifecycleOwner, {
                it?.let {
                    binding.run {
                        (recyclerView.adapter as FlightsListAdapter).submitList(it)
                        sortByPrice.isVisible = true
                        sortByDeparture.isVisible = true
                    }
                }
            })
            errorLiveData.observe(viewLifecycleOwner, Observer {
                it?.let { showErrorDialog() }
            })
        }
        viewModel.fetchFlightsList()
    }

}