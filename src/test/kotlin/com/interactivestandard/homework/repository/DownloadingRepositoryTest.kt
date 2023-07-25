package com.interactivestandard.homework.repository

import com.interactivestandard.homework.model.RemoteDownloadingRequest
import com.interactivestandard.homework.repository.http.DownloadingRepository
import org.junit.jupiter.api.Test

class DownloadingRepositoryTest {
    val repo = DownloadingRepository()

    @Test
    fun test() {
        repo.download(RemoteDownloadingRequest(height = 240, width = 320))
        println()
    }
}