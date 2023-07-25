package com.interactivestandard.homework.model

class DbFilesUpdateRequest(
    val url: String,
    val size: FileSizeInBytes,
    val contentType: String,
    val content: ByteArray
)