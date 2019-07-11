package com.example.app.web

import org.springframework.web.reactive.function.server.router

fun routes(pizzaHandler: PizzaHandler) = router {
    "/api/pizza".nest {
        GET("/", pizzaHandler::findAll)
        GET("/{id}", pizzaHandler::findById)
    }
}
