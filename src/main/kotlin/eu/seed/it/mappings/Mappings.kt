package eu.seed.it.mappings

import java.time.LocalDate

data class Seed(
        val id: Int,
        val name: String,
        val description: String,
        val tips: String? = null
)

data class User(
        val id: Int,
        val name: String,
        val lastName: String,
        val birthDate: LocalDate,
        val email: String,
        val password: String,
        val subscription: String,
        val location: String
)