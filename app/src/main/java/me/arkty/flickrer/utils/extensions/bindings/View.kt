package me.arkty.flickrer.utils.extensions.bindings

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun View.setVisibleIf(v: Boolean) {
    visibility = if (v) View.VISIBLE else View.GONE
}