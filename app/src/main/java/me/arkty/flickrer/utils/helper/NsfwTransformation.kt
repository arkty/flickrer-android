package me.arkty.flickrer.utils.helper

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.charset.Charset
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NsfwTransformation @Inject constructor(
    private val detector: NsfwDetectHelper
) : BitmapTransformation() {

    override fun transform(
        pool: BitmapPool, toTransform: Bitmap,
        outWidth: Int, outHeight: Int
    ): Bitmap {
        detector.test(toTransform)
        return toTransform
    }

    override fun equals(other: Any?): Boolean {
        return other is NsfwTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    companion object {
        private val ID = "com.bumptech.glide.transformations.NSFWTransformation"
        private val ID_BYTES: ByteArray = ID.toByteArray(Charset.forName("UTF-8"))
    }
}