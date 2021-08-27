package me.arkty.flickrer.utils.extensions.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import me.arkty.flickrer.utils.helper.NsfwTransformation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NsfwImageAdapters @Inject constructor(
    private val nsfwTransformation: NsfwTransformation
) {
    @BindingAdapter("imageUrlSafe")
    fun imageUrlSafe(v: ImageView, url: String) {
        Glide.with(v.context)
            .load(url)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(CenterCrop(), nsfwTransformation)
                )
            )
            .into(v)
    }
}