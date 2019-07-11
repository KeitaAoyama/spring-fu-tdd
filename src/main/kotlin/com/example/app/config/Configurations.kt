package com.example.app.config

import com.example.app.repository.PizzaRepository
import com.example.app.web.PizzaHandler
import com.example.app.web.routes
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.r2dbcH2
import org.springframework.fu.kofu.webflux.webFlux

val DBConfig = configuration {
    val properties = configurationProperties<H2Properties>(prefix = "settings.h2")
    r2dbcH2 {
        url = properties.url
        username = properties.username
        password = properties.password
    }
}

val dataConfig = configuration {
    beans {
        bean<PizzaRepository>()
    }
    listener<ApplicationReadyEvent> {
        ref<PizzaRepository>().init()
    }
    enable(DBConfig)
}

val webConfig = configuration {
    beans {
        bean<PizzaHandler>()
        bean(::routes)
    }
    webFlux {
        port = if (profiles.contains("test")) 8181 else 8080
        codecs {
            string()
            jackson{
                indentOutput = true
            }
        }
    }
}
