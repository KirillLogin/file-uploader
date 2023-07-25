package com.interactivestandard.homework

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class HomeworkApplication

fun main(args: Array<String>) {
    runApplication<HomeworkApplication>(*args)
}
