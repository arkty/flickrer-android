package me.arkty.flickrer.utils.extensions.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(this.context)
        .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
        .load(url)
        .into(this)
}