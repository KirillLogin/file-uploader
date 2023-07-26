package com.interactivestandard.homework.repository.http

import com.interactivestandard.homework.model.BYTES_IN_KB
import com.interactivestandard.homework.model.DbFilesUpdateRequest
import com.interactivestandard.homework.model.RemoteDownloadingRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient


@Component
class DownloadingRepository(
    @Value("\${processing.max-file-size-kb:500}") private val maxFileSizeKb: Int
) {

    companion object {
        private val LOG = LoggerFactory.getLogger(this::class.java)
    }

    private val webClient = WebClient.builder()
        .clientConnector(ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))
        .codecs { it.defaultCodecs().maxInMemorySize(maxFileSizeKb * BYTES_IN_KB) }
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
                val size = it.body?.size?.toLong() ?: 0
                LOG.info("Downloaded $url, size ${size/BYTES_IN_KB} Kb")
                DbFilesUpdateRequest(
                    url = url,
                    size = size,
                    contentType = it.headers.contentType.toString(),
                    content = it.body ?: ByteArray(0)
                )
            }
    }
}