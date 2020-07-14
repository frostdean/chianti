package com.wmjun.chianti.presentation.controllers.common

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/_internal")
class MonitoringController {

    @GetMapping("/health-check")
    fun check(): String = "OK"

}