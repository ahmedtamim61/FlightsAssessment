package com.example.flightassessment.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object FlightsBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:url")
    fun loadImageUrl(imageView: AppCompatImageView, url : String) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}