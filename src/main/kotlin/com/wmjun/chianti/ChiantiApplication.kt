package com.wmjun.chianti

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class ChiantiApplication

fun main(args: Array<String>) {
    runApplication<ChiantiApplication>(*args)
}
