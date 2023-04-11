package com.theideal.goride.ui.driver.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.theideal.goride.databinding.CardSettingBinding

class ProfileDriverAdapter() :
    ListAdapter<Setting, ProfileDriverAdapter.ProfileDriverViewHolder>(ProfileDriverDiffUtil()) {
    class ProfileDriverDiffUtil : DiffUtil.ItemCallback<Setting>() {
        override fun areItemsTheSame(oldItem: Setting, newItem: Setting): Boolean {
            return oldItem.nameOfTheSetting == newItem.nameOfTheSetting
        }

        override fun areContentsTheSame(oldItem: Setting, newItem: Setting): Boolean {
            return oldItem == newItem
        }
    }

    class ProfileDriverViewHolder(val bind: CardSettingBinding) : ViewHolder(bind.root) {
        fun bind(setting: Setting) {
            bind.setting = setting
            bind.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileDriverViewHolder {
        return ProfileDriverViewHolder(
            CardSettingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileDriverViewHolder, position: Int) {
        val setting = getItem(position)
        holder.bind(setting)

    }
}