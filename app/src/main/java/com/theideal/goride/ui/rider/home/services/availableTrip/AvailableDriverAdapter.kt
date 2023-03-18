package com.theideal.goride.ui.rider.home.services.availableTrip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.goride.databinding.CardDetailListOfDirverBinding
import com.theideal.goride.databinding.CardListOfDriverBinding
import com.theideal.goride.model.DriverStatus

class AvailableDriverAdapter :
    ListAdapter<DriverStatus, AvailableDriverAdapter.ViewHolder>(DiffCallBack) {
    object DiffCallBack : DiffUtil.ItemCallback<DriverStatus>() {
        override fun areItemsTheSame(oldItem: DriverStatus, newItem: DriverStatus): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: DriverStatus, newItem: DriverStatus): Boolean {
            TODO("Not yet implemented")
        }

    }

    class ViewHolder(private val binding: CardDetailListOfDirverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(driverStatus: DriverStatus) {
            binding.driverStatus = driverStatus
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableDriverAdapter.ViewHolder {
        return ViewHolder(
            CardDetailListOfDirverBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AvailableDriverAdapter.ViewHolder, position: Int) {
        var driverPosition = getItem(position)
        holder.bind(driverPosition)
    }
}