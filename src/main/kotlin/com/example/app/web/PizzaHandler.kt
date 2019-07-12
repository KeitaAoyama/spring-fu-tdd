package com.example.app.web

import com.example.app.domain.PizzaRepository
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body

@Suppress("UNUSED_PARAMETER")
class PizzaHandler(private val repository: PizzaRepository) {

    fun findAll(request: ServerRequest) = ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(repository.findAll())

    fun findById(request: ServerRequest) = ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(repository.findById(request.pathVariable("id").toLong()))

}