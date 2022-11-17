package com.ihexep.presentation.utils

import android.graphics.*
import coil.size.Size
import coil.transform.Transformation

// Simple custom transformation for Coil image load library
// which takes center square from wide image
class CenterSquareTransformation : Transformation {

    override val cacheKey: String = javaClass.name

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val output: Bitmap

        if (input.width <= input.height) return input

        val x = input.width / 2 - input.height / 2
        output = Bitmap.createBitmap(input, x,0, input.height, input.height)
        return output
    }

}