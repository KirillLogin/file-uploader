package com.interactivestandard.homework.model

import kotlin.random.Random

class RemoteDownloadingRequest(
    val width: Int,
    val height: Int
) {
    companion object {
        fun generate(): RemoteDownloadingRequest = RemoteDownloadingRequest(
            height = Random.nextInt(MIN_IMAGE_SIZE_PROPERTY, MAX_IMAGE_SIZE_PROPERTY),
            width = Random.nextInt(MIN_IMAGE_SIZE_PROPERTY, MAX_IMAGE_SIZE_PROPERTY)
        )
    }
}