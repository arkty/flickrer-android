package me.arkty.flickrer.utils.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import java.lang.IllegalArgumentException

class DataClassDiff<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        if (oldItem::class.isData) {
            return oldItem == newItem
        } else {
            throw IllegalArgumentException("All items passed to DataClassDiff must be data classes")
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        if (oldItem::class.isData) {
            return oldItem == newItem
        } else {
            throw IllegalArgumentException("All items passed to DataClassDiff must be data classes")
        }
    }
}