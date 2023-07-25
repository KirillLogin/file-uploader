package com.interactivestandard.homework.model

import kotlin.random.Random

class RemoteDownloadingRequest(
    val width: Pixel,
    val height: Pixel
) {
    companion object {
        private const val min = 10
        private const val max = 5000

        fun generate(): RemoteDownloadingRequest = RemoteDownloadingRequest(
            height = Random.nextInt(min, max),
            width = Random.nextInt(min, max)
        )
    }
}