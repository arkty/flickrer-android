package me.arkty.flickrer.utils.helper

import android.content.Context
import android.graphics.Bitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import me.arkty.flickrer.ml.SavedModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NsfwDetectHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val model = SavedModel.newInstance(context)

    // type: float32[1,224,224,3]
    private val imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
        .build()

    fun test(b: Bitmap) {

        val buffer = TensorImage(DataType.FLOAT32).let {
            it.load(b)
            imageProcessor.process(it)
        }.tensorBuffer

        val outputs = model.process(buffer)
        val probability = outputs.outputFeature0AsTensorBuffer
        Timber.d("probability: ${probability.floatArray.joinToString(", ")}")
    }
}