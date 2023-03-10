package com.theideal.goride

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theideal.goride.model.*
import com.theideal.goride.ui.rider.home.services.availableTrip.AvailableTripAdapter
import com.theideal.goride.ui.rider.home.services.availableTrip.CardViewAdapter


@BindingAdapter("list_data")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CardViewData>?) {
    val adapter = recyclerView.adapter as CardViewAdapter
    adapter.submitList(data)
}
@BindingAdapter("list_data_available_trip")
fun bindRecyclerView1(recyclerView: RecyclerView, data: List<Trip>?) {
    val adapter = recyclerView.adapter as AvailableTripAdapter
    adapter.submitList(data)
}


@BindingAdapter("set_image")
fun setImage(view: ImageView, card: CardViewData?) {
    Glide.with(view.context)
        .load(card!!.image)
        .centerCrop()
        .placeholder(R.drawable.vc_error)
        .into(view)
}

@BindingAdapter("set_image_image_name")
fun setImageFromUrl(view: ImageView, imageName: String?) {
    Glide.with(view.context)
        .load(imageName!!)
        .centerCrop()
        .placeholder(R.drawable.vc_error)
        .into(view)
}


@BindingAdapter("set_user_type")
fun setUserType(view: AutoCompleteTextView, user: User?) {
    val adapter = ArrayAdapter(
        view.context,
        android.R.layout.simple_list_item_1,
        listOf("Rider", "Driver")
    )
    view.setAdapter(adapter)
    view.setOnItemClickListener { parent, _, position, _ ->
        user!!.userType = parent.getItemAtPosition(position).toString()
    }
}

@BindingAdapter("set_vehicle_type")
fun setVehicleType(view: AutoCompleteTextView, carType: Driver?) {
    val adapter = ArrayAdapter(
        view.context,
        android.R.layout.simple_list_item_1,
        listOf("Taxi", "TyneBus", "MicroBus", "MiniBus", "Bus")
    )
    view.setAdapter(adapter)
    view.setOnItemClickListener { parent, _, position, _ ->
        carType!!.carType = parent.getItemAtPosition(position).toString()
    }
}

