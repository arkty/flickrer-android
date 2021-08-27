package me.arkty.flickrer.utils.helper

import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import me.arkty.flickrer.ml.SavedModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.common.ops.QuantizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.min

@Singleton
class NsfwDetectHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val model = SavedModel.newInstance(context)

    // type: float32[1,224,224,3]
    private val imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
        .add(NormalizeOp(0f, 255f))
        .build()

    fun isSafeForWork(b: Bitmap, tag: String = "") : Boolean {
        synchronized(NsfwDetectHelper::class) {
            val size = min(b.width, b.height)
            val squared = ThumbnailUtils.extractThumbnail(b, size, size)

            val buffer = TensorImage(DataType.FLOAT32).let {
                it.load(squared)
                imageProcessor.process(it)
            }.tensorBuffer
            //Timber.d("buffer = ${buffer.floatArray.joinToString(",")}")
            val outputs = model.process(buffer)
            val probability = outputs.outputFeature0AsTensorBuffer
            Timber.d("probability[$tag]: ${probability.floatArray.joinToString(", ")}")
        }
        return true
    }

    companion object{
        //const val LABELS = listOf()
    }
}