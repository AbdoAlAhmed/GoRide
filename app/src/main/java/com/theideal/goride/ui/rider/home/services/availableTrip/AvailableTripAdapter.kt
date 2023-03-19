package com.theideal.goride.ui.rider.home.services.availableTrip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.goride.databinding.CardAvailableTripsBinding
import com.theideal.goride.model.Trip

class AvailableTripAdapter(val onClickListener: OnClick): ListAdapter<Trip , AvailableTripAdapter.ViewHolder>(DiffCallBack) {
    object DiffCallBack: DiffUtil.ItemCallback<Trip>() {
        override fun areItemsTheSame(oldItem: Trip, newItem: Trip): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Trip, newItem: Trip): Boolean {
            return oldItem.tripId == newItem.tripId
        }

    }
    class ViewHolder (val binding : CardAvailableTripsBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: Trip) {
            binding.trip = trip
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableTripAdapter.ViewHolder {
        return ViewHolder(
            CardAvailableTripsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AvailableTripAdapter.ViewHolder, position: Int) {
        val trip = getItem(position)
        holder.bind(trip)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(trip)
        }
    }

    class OnClick(private val clickListener: (trip: Trip) -> Unit){
        fun onClick (trip: Trip) = clickListener(trip)
    }

}