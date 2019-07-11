package com.example.app

import com.example.app.config.dataConfig
import com.example.app.config.webConfig
import org.springframework.boot.WebApplicationType
import org.springframework.fu.kofu.application

val app = application(WebApplicationType.REACTIVE) {
    enable(dataConfig)
    enable(webConfig)
}

fun main() {
    app.run()
}

