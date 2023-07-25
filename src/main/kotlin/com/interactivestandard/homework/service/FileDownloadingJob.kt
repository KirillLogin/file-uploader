package com.interactivestandard.homework.service

import com.interactivestandard.homework.model.RemoteDownloadingRequest
import com.interactivestandard.homework.repository.db.FilesRepository
import com.interactivestandard.homework.repository.db.StatsRepository
import com.interactivestandard.homework.repository.http.DownloadingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class FileDownloadingJob(
    private val fileDownloadingRepo: DownloadingRepository,
    private val fileSavingRepo: FilesRepository,
    private val statsRepository: StatsRepository,
    @Value("\${processing.parallelism:5}") private val parallelism: Int = 5
) {

    companion object {
        private val LOG = LoggerFactory.getLogger(this::class.java)
        private val SCOPE = CoroutineScope(Dispatchers.IO + SupervisorJob())
        private val BYTES_MULTIPLIER = 1024

        //every 5 min
        private const val DEFAULT_CRON_EXPRESSION = "0 0/5 * ? * *"
    }

    @Scheduled(cron = "\${processing.cron:${DEFAULT_CRON_EXPRESSION}}")
    fun execute() = SCOPE.launch {
        LOG.info("Starting the downloading")
        (0 until parallelism)
            .map { RemoteDownloadingRequest.generate() }
            .map { request ->
                async {
                    runCatching {
                        fileDownloadingRepo.download(request)
                            ?.let { fileSavingRepo.saveFilesData(it) }
                            ?: 0
                    }
                        .onFailure { LOG.error("Unable to process request $request", it) }
                        .getOrElse { 0 }
                }
            }
            .awaitAll()
            .filter { it > 0 }
            .run {
                val totalBytes = sum()
                LOG.info("$size files downloaded with total size ${totalBytes/BYTES_MULTIPLIER} Kb")
                statsRepository.update(countIncrement = size, sizeIncrement = totalBytes)
            }
    }
}