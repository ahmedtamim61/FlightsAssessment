package com.example.flightassessment.ui

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.flightassessment.R

open class BaseFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })
    }

    fun showErrorDialog() {
        AlertDialog.Builder(requireContext()).setPositiveButton(R.string.ok) {
                dialog, _ -> dialog.dismiss()
        }.create().show()
    }
}