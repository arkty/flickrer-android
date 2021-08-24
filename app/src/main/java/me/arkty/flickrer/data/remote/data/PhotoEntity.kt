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
) : java.io.Serializable { // Serializable to pass in bundle

    // this actually should be moved to UI data,
    // but in this case we can reuse PhotoEntity as UI data
    val thumbUrl by lazy { FlickrUrlBuilder.buildThumbUrl(this) }
    val normalUrl by lazy { FlickrUrlBuilder.buildLargeUrl(this) }
    val fullUrl by lazy { FlickrUrlBuilder.buildFullUrl(this) }
}