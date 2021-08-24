package me.arkty.flickrer.data.remote

import me.arkty.flickrer.data.remote.data.PhotoEntity

class FlickrUrlBuilder {
    companion object {
        fun buildThumbUrl(p: PhotoEntity): String {
            return "${BASE_URL}/${p.server}/${p.id}_${p.secret}_m.jpg"
        }

        private const val BASE_URL = "https://live.staticflickr.com"
    }
}