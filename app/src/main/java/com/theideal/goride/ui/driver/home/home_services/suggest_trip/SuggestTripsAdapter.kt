package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.goride.databinding.CardTripsSuggestionBinding
import com.theideal.goride.model.TripsLine

class SuggestTripsAdapter(private val onClick: OnClick) :
    ListAdapter<TripsLine, SuggestTripsAdapter.SuggestTripsViewHolder>(TripsLineDiffCallback) {
    object TripsLineDiffCallback : DiffUtil.ItemCallback<TripsLine>() {
        override fun areItemsTheSame(oldItem: TripsLine, newItem: TripsLine): Boolean {
            return oldItem.tripsLineId == newItem.tripsLineId
        }

        override fun areContentsTheSame(oldItem: TripsLine, newItem: TripsLine): Boolean {
            return oldItem == newItem
        }
    }

    class SuggestTripsViewHolder(val binding: CardTripsSuggestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tripsLine: TripsLine) {
            binding.tripsLine = tripsLine
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestTripsViewHolder {
        return SuggestTripsViewHolder(
            CardTripsSuggestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SuggestTripsViewHolder, position: Int) {
        val tripsLine = getItem(position)
        holder.bind(tripsLine)
        holder.itemView.setOnClickListener {
            onClick.clickListener(tripsLine)
        }
    }

    class OnClick(val clickListener: (tripsLine: TripsLine) -> Unit) {
        fun onClick(tripsLine: TripsLine) = clickListener(tripsLine)
    }
}