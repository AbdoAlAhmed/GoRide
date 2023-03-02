package com.theideal.goride.ui.rider.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.goride.databinding.CardHomeRiderBinding
import com.theideal.goride.model.CardViewData

class CardViewAdapter(private val onClickListener: OnClick) :
    ListAdapter<CardViewData, CardViewAdapter.CardViewHolder>(DiffCallBack) {
    object DiffCallBack : DiffUtil.ItemCallback<CardViewData>() {
        override fun areItemsTheSame(oldItem: CardViewData, newItem: CardViewData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CardViewData, newItem: CardViewData): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class CardViewHolder(private val binding: CardHomeRiderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cardViewData: CardViewData) {
            binding.card = cardViewData
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        return CardViewHolder(
            CardHomeRiderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = getItem(position)
        holder.bind(card)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(card)
        }

    }

    class OnClick(val onClickListener: (CardViewData) -> Unit) {
        fun onClick(card: CardViewData) = onClickListener(card)
    }

}


