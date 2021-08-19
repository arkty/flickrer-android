package me.arkty.flickrer.data.remote.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotosResponse(
    @SerialName("page")
    val page: Int,

    @SerialName("pages")
    val pages: Int,

    @SerialName("perpage")
    val perPage: Int,

    @SerialName("total")
    val total: Int,

    @SerialName("photo")
    val photo: List<PhotoEntity>
)