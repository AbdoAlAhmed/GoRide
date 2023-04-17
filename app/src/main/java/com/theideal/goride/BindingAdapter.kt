package com.theideal.goride

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theideal.goride.model.*
import timber.log.Timber


@BindingAdapter("bind_data")
fun bindDataToRecyclerView(recyclerView: RecyclerView, data: List<Any>?) {
    val adapter = recyclerView.adapter as androidx.recyclerview.widget.ListAdapter<*, *>?
    adapter!!.submitList(data as List<Nothing>?)
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
        Timber.e("focus" + e)
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

@BindingAdapter("isHSupported")
fun isHSupported(view: View, boolean: Boolean) {
    if (boolean) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("text_int")
fun textInt(textView: TextView, int: Int) {
    textView.text = int.toString()
}

@BindingAdapter("text_double")
fun setTextDouble(view: EditText, value: Double?) {
    view.setText(value?.toString())
}

@InverseBindingAdapter(attribute = "text_double" )
fun getTextDouble(view: EditText): Double {
    val text = view.text.toString()
    return if (text.isNotEmpty()) text.toDouble() else 0.0
}


@InverseBindingAdapter(attribute = "android:text")
fun getTextDouble2(view: EditText): Double {
    val text = view.text.toString()
    return if (text.isNotEmpty()) text.toDouble() else 0.0
}
