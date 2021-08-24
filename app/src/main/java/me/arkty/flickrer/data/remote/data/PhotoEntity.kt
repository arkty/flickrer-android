package me.arkty.flickrer.data.remote.data

import kotlinx.serialization.Serializable
import me.arkty.flickrer.data.remote.FlickrUrlBuilder

@Serializable
data class PhotoEntity(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
) {

    // this actually should be moved to UI data, but in this case we can reuse it
    val thumbUrl by lazy { FlickrUrlBuilder.buildThumbUrl(this) }
}