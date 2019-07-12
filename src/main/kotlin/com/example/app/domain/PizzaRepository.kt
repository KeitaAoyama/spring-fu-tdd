package com.example.app.domain

import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.asType
import org.springframework.data.r2dbc.core.into

class PizzaRepository(private val client: DatabaseClient) {

    fun findAll() = client
            .select()
            .from("PIZZA")
            .asType<Pizza>()
            .fetch()
            .all()

    fun findById(id: Long) = client
            .execute("SELECT * FROM PIZZA WHERE ID = :id")
            .bind("id", id)
            .asType<Pizza>()
            .fetch()
            .one()

    private fun insert(pizza: Pizza) = client
            .insert()
            .into<Pizza>()
            .table("PIZZA")
            .using(pizza)
            .then()

    private fun deleteAll() = client
            .execute("DELETE FROM PIZZA")
            .fetch()
            .one()
            .then()

    fun init() = client
            .execute("CREATE TABLE IF NOT EXISTS PIZZA (ID　BIGINT　NOT NULL AUTO_INCREMENT PRIMARY KEY, STYLE VARCHAR(25) NOT NULL,);")
            .then()
            .then(deleteAll())
            .then(insert(Pizza(1L, "New Heaven Style")))
            .then(insert(Pizza(2L, "California Style")))
            .then(insert(Pizza(3L, "Chicago Thin Crust")))
            .then(insert(Pizza(4L, "Detroit Style")))
            .then(insert(Pizza(5L, "Upside-down")))
            .block()

}