package com.theideal.goride.ui.driver.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.goride.databinding.CardServicesBinding
import com.theideal.goride.model.CardViewData

class HomeDriverAdapter(private val onClickListener: OnClick) : ListAdapter<CardViewData, HomeDriverAdapter.ViewHolder>(DiffCallBack) {

    object DiffCallBack : DiffUtil.ItemCallback<CardViewData>() {
        override fun areItemsTheSame(oldItem: CardViewData, newItem: CardViewData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CardViewData, newItem: CardViewData): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class ViewHolder(private val binding: CardServicesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cardViewData: CardViewData) {
            binding.card = cardViewData
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            CardServicesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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