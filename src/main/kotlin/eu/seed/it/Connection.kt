package eu.seed.it

data class Connection(val address: String, val port: Int) {
    init {
        if (port !in 0..65535) throw IllegalArgumentException("$port not in 0.65535")
    }

    override fun toString(): String {
        return "$address:$port"
    }
}

data class DatabaseConnection(
        val address: String,
        val port: Int,
        val name: String,
        val user: String,
        val password: String
) {
    init {
        if (port !in 0..65535) throw IllegalArgumentException("$port not in 0.65535")
    }

    override fun toString(): String {
        return "$address:$port"
    }
}
