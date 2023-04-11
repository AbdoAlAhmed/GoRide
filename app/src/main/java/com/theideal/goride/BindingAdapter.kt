package com.theideal.goride

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theideal.goride.model.*
import com.theideal.goride.ui.rider.CardViewAdapter
import com.theideal.goride.ui.rider.home.services.availableTrip.AvailableDriverAdapter
import com.theideal.goride.ui.rider.home.services.availableTrip.AvailableTripAdapter


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

@BindingAdapter("list_data_of_available_drivers")
fun bindRecyclerView2(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as AvailableDriverAdapter
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

@BindingAdapter("set_image_users")
fun setImage(view: ImageView, user: User?) {
    try {
        Glide.with(view.context)
            .load(user!!.profileImage)
            .centerCrop()
            .placeholder(R.drawable.vc_error)
            .into(view)
    } catch (e: Exception) {
        e.printStackTrace()
    }

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
fun setVehicleType(view: AutoCompleteTextView, carType: Car?) {
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

@BindingAdapter("set_when_to_ride")
fun setWhenToRide(autoComplete: AutoCompleteTextView, rideWhen: Trip) {
    val adapter = ArrayAdapter(
        autoComplete.context,
        android.R.layout.simple_list_item_1,
        listOf("Now", "When I'm around")
    )
    autoComplete.setAdapter(adapter)
    autoComplete.setOnItemClickListener { parent, _, position, l ->
        rideWhen!!.whenToRide = parent.getItemAtPosition(position).toString()
    }
}

@BindingAdapter("set_location_text")
fun setLocationText(textView: TextView, location: Location?) {
    location?.let {
        textView.text = it.destinationName
    }
}

@BindingAdapter("get_driver_status")
fun getDriverStatus(switch: Switch, boolean: Boolean) {
    switch.isChecked = boolean
}

@BindingAdapter("text_visibility")
fun textVisibility(textView: TextView, card: CardViewData?) {
    if (card!!.label.isEmpty()) {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
        textView.text = card.label
    }
}

@BindingAdapter("loading_data")
fun loadingData(progressBar: ProgressBar, name: User?) {
    if (name!!.firstName.isEmpty()) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }
}




