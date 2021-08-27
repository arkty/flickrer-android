package me.arkty.flickrer.utils.helper

import android.graphics.Bitmap
import androidx.renderscript.Allocation
import androidx.renderscript.Element
import androidx.renderscript.RenderScript
import androidx.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.charset.Charset
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.text.Typography.times

@Singleton
class NsfwTransformation @Inject constructor(
    private val detector: NsfwDetectHelper,
    private val rs: RenderScript
) : BitmapTransformation() {

    override fun transform(
        pool: BitmapPool, toTransform: Bitmap,
        outWidth: Int, outHeight: Int
    ): Bitmap {
        if (!detector.isSafeForWork(toTransform)) {
            toTransform.blur()
        }
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

    private fun Bitmap.blur() {
        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        script.setRadius(25f)

        (0..9).forEach {
            //use this constructor for best performance, because it uses USAGE_SHARED mode which reuses memory
            val input = Allocation.createFromBitmap(rs, this)
            val output = Allocation.createTyped(rs, input.type);


            script.setInput(input)
            script.forEach(output)
            output.copyTo(this)
        }
    }

    companion object {
        private val ID = "com.bumptech.glide.transformations.NSFWTransformation"
        private val ID_BYTES: ByteArray = ID.toByteArray(Charset.forName("UTF-8"))
    }
}