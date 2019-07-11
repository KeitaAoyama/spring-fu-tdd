package com.example.app

import com.example.app.model.Pizza
import com.example.app.repository.PizzaRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.getBean
import org.springframework.context.ConfigurableApplicationContext
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PizzaRepositoryTests {

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `Find one pizza by id`() {
        val repository: PizzaRepository = context.getBean()
        StepVerifier
                .create(repository.findById(1L))
                .expectNext(Pizza(1L, "New Heaven Style"))
                .verifyComplete()
    }

    @Test
    fun `Find all pizzas`() {
        val repository: PizzaRepository = context.getBean()
        StepVerifier
                .create(repository.findAll())
                .expectNext(Pizza(1L, "New Heaven Style"))
                .expectNext(Pizza(2L, "California Style"))
                .expectNext(Pizza(3L, "Chicago Thin Crust"))
                .expectNext(Pizza(4L, "Detroit Style"))
                .expectNext(Pizza(5L, "Upside-down"))
                .verifyComplete()
    }

    @Test
    fun `Initialize pizza table`(){
        val repository: PizzaRepository = context.getBean()
        repository.init()
        StepVerifier
                .create(repository.findAll())
                .expectNext(Pizza(1L, "New Heaven Style"))
                .expectNext(Pizza(2L, "California Style"))
                .expectNext(Pizza(3L, "Chicago Thin Crust"))
                .expectNext(Pizza(4L, "Detroit Style"))
                .expectNext(Pizza(5L, "Upside-down"))
                .verifyComplete()
    }


    @AfterAll
    fun afterAll() {
        context.close()
    }

}