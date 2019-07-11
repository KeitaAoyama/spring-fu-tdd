package com.example.app

import com.example.app.model.Pizza
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTests {

    private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181/api").build()
    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `Get one pizza`() {
        client.get().uri("/pizza/{id}", "1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody<Pizza>()
                .isEqualTo(Pizza(1L, "New Heaven Style"))
    }

    @Test
    fun `Get all pizzas`() {
        client.get().uri("/pizza/")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBodyList<Pizza>()
                .hasSize(5)
                .contains(Pizza(1L, "New Heaven Style"))
                .contains(Pizza(2L, "California Style"))
                .contains(Pizza(3L, "Chicago Thin Crust"))
                .contains(Pizza(4L, "Detroit Style"))
                .contains(Pizza(5L, "Upside-down"))
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }

}