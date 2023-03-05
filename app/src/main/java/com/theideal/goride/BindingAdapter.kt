package com.theideal.goride

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theideal.goride.model.CardViewData
import com.theideal.goride.model.User
import com.theideal.goride.ui.rider.adatper.CardViewAdapter


@BindingAdapter("list_data")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CardViewData>?) {
    val adapter = recyclerView.adapter as CardViewAdapter
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

@BindingAdapter("validate_email_password")
fun validateEmailPassword(view: Button, user: User) {
    if (user.email.isNotEmpty() && user.getPassword().isNotEmpty()) {
        view.isEnabled = true
    } else {
        view.isEnabled = false
    }
}

