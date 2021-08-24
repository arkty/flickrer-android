package me.arkty.flickrer.data.remote

import me.arkty.flickrer.data.remote.data.PhotoEntity

class FlickrUrlBuilder {
    companion object {
        fun buildThumbUrl(p: PhotoEntity) = buildUrl(p, SIZE_SUFFIX_THUMB)
        fun buildLargeUrl(p: PhotoEntity) = buildUrl(p, SIZE_SUFFIX_LARGE)
        fun buildFullUrl(p: PhotoEntity) = buildUrl(p, SIZE_SUFFIX_FULL)

        fun buildLocalFilename(p: PhotoEntity) = "${p.id}.${FORMAT}"

        private fun buildUrl(p: PhotoEntity, sizeSuffix: String) =
            "${BASE_URL}/${p.server}/${p.id}_${p.secret}_${sizeSuffix}.${FORMAT}"


        private const val BASE_URL = "https://live.staticflickr.com"
        private const val SIZE_SUFFIX_THUMB = "m"
        private const val SIZE_SUFFIX_LARGE = "b"

        /**
         * SIZE_SUFFIX_FULL is same as large, because we need
         * an addition secret to download full image
         */
        private const val SIZE_SUFFIX_FULL = "b"
        private const val FORMAT = "jpg"
    }
}