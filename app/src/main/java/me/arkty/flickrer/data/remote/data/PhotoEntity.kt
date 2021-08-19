package me.arkty.flickrer.data.remote.data

import kotlinx.serialization.Serializable

@Serializable
data class PhotoEntity(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
)