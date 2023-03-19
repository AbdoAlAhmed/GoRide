package com.theideal.goride.ui.rider.home.services.availableTrip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.goride.databinding.CardDetailListOfDriverBinding
import com.theideal.goride.model.DriverStatus

class AvailableDriverAdapter(private val onClickListener: OnClick) :
    ListAdapter<DriverStatus, AvailableDriverAdapter.ViewHolder>(DiffCallBack) {
    object DiffCallBack : DiffUtil.ItemCallback<DriverStatus>() {
        override fun areItemsTheSame(oldItem: DriverStatus, newItem: DriverStatus): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DriverStatus, newItem: DriverStatus): Boolean {
            return oldItem.statusId == newItem.statusId
        }

    }

    class ViewHolder(private val binding: CardDetailListOfDriverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(driverStatus: DriverStatus) {
            binding.driverStatus = driverStatus
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            CardDetailListOfDriverBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var driverPosition = getItem(position)
        holder.bind(driverPosition)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(driverPosition)
        }
    }

    class OnClick(val onClickListener: (DriverStatus) -> Unit) {
        fun onClick(status: DriverStatus) = onClickListener(status)
    }
}