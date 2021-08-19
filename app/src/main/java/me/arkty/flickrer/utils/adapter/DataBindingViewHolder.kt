package me.arkty.flickrer.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder<T : ViewDataBinding>(
    parent: ViewGroup,
    layout: Int
) : RecyclerView.ViewHolder(
    DataBindingUtil.inflate<T>(
        LayoutInflater.from(parent.context),
        layout, parent, false
    ).root
) {
    val binding by lazy { DataBindingUtil.findBinding<T>(itemView) }
}