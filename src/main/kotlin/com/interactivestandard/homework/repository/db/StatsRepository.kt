package com.interactivestandard.homework.repository.db

import com.interactivestandard.homework.model.FileSizeInBytes
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class StatsRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun update(countIncrement: Int, sizeIncrement: FileSizeInBytes) {
        if (countIncrement == 0 && sizeIncrement == 0L) {
            return
        }
        jdbcTemplate.update(
            """
            update summary set files_count = files_count + ?, files_size_bytes = files_size_bytes + ?
            """.trimIndent(),
            countIncrement,
            sizeIncrement
        )
    }
}