package com.theideal.goride.ui.rider.home.services.availableTrip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.goride.databinding.CardDetailListOfDriverBinding
import com.theideal.goride.model.User

class AvailableDriverAdapter(private val onClickListener: OnClick) :
    ListAdapter<User, AvailableDriverAdapter.ViewHolder>(DiffCallBack) {
    object DiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class ViewHolder(private val binding: CardDetailListOfDriverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
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
        var user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(user)
        }
    }

    class OnClick(val onClickListener: (User) -> Unit) {
        fun onClick(status: User) = onClickListener(status)
    }
}