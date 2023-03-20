package com.sosyal.api.util

import com.cloudinary.Cloudinary

object CloudinaryUtil {
    private val cloudinary = Cloudinary(System.getenv("CLOUDINARY_URL"))

    fun uploadImage(image: ByteArray): Map<Any?, Any?> =
        cloudinary.uploader().upload(image, mapOf("folder" to "sosyal_user_avatar"))
}