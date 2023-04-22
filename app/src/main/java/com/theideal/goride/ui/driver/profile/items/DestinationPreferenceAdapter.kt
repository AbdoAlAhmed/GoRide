package com.theideal.goride.ui.driver.profile.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.theideal.goride.R
import com.theideal.goride.databinding.ItemChooseTripDriverBinding
import com.theideal.goride.model.TripsLine

class DestinationPreferenceAdapter(private val onClick: OnClickListener) :
    ListAdapter<TripsLine, DestinationPreferenceAdapter.ViewHolder>(DiffCallBack) {
    object DiffCallBack : DiffUtil.ItemCallback<TripsLine>() {
        override fun areItemsTheSame(oldItem: TripsLine, newItem: TripsLine): Boolean {
            return oldItem.tripsLineId == newItem.tripsLineId
        }

        override fun areContentsTheSame(oldItem: TripsLine, newItem: TripsLine): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(private val binding: ItemChooseTripDriverBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(tripsLine: TripsLine) {
            binding.tripsLine = tripsLine
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChooseTripDriverBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val position = getItem(position)
        holder.bind(position)
        holder.itemView.setOnClickListener {
            onClick.onClick(position)
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                it.setBackgroundResource(R.color.sunset_orange)
            } else {
                it.setBackgroundResource(R.color.white)
            }
        }
    }

    class OnClickListener(val clickListener: (tripsLine: TripsLine) -> Unit) {
        fun onClick(tripsLine: TripsLine) = clickListener(tripsLine)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}