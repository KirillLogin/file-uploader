package com.interactivestandard.homework.repository.db

import com.interactivestandard.homework.model.DbFilesUpdateRequest
import com.interactivestandard.homework.model.FileSizeInBytes
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class FilesRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun saveFilesData(request: DbFilesUpdateRequest): FileSizeInBytes {
        jdbcTemplate.update(
            """
            insert into files (url, size_bytes, content_type, content) 
            values (?, ?, ?, ?)
            """.trimIndent(),
            request.url,
            request.size,
            request.contentType,
            request.content
        )
        return request.size
    }
}