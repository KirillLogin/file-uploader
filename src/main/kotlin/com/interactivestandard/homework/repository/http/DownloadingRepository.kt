package com.interactivestandard.homework.repository.http

import com.interactivestandard.homework.model.DbFilesUpdateRequest
import com.interactivestandard.homework.model.RemoteDownloadingRequest
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Component
class DownloadingRepository {
    private val webClient = WebClient.builder()
        .clientConnector(ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))
        .build()

    fun download(request: RemoteDownloadingRequest): DbFilesUpdateRequest? {
        val url = "https://loremflickr.com/${request.width}/${request.height}"
        return webClient
            .get()
            .uri(url)
            .retrieve()
            .toEntity(ByteArray::class.java)
            .block()
            ?.let {
                DbFilesUpdateRequest(
                    url = url,
                    size = it.body?.size?.toLong() ?: 0,
                    contentType = it.headers.contentType.toString(),
                    content = it.body ?: ByteArray(0)
                )
            }
    }
}