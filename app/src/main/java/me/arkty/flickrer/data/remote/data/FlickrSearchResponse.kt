package me.arkty.flickrer.data.remote.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlickrSearchResponse(
    @SerialName("stat")
    val stat: String
) {
}