package com.theideal.goride

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theideal.goride.model.CardViewData
import com.theideal.goride.ui.rider.adatper.CardViewAdapter


@BindingAdapter("list_data")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CardViewData>?) {
    val adapter = recyclerView.adapter as CardViewAdapter
    adapter.submitList(data)
}

@BindingAdapter("set_image")
fun setImage (view: ImageView, card:CardViewData?){
    Glide.with(view.context)
        .load(card!!.image)
        .centerCrop()
        .placeholder(R.drawable.vc_error)
        .into(view)
}