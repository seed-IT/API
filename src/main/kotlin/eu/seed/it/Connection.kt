package eu.seed.it

data class Connection(val address: String, val port: Port) {

    override fun toString(): String {
        return "$address:$port"
    }
}

data class DatabaseConnection(
        val address: String,
        val port: Port,
        val name: String,
        val user: String,
        val password: String
) {
    override fun toString(): String {
        return "$address:$port"
    }
}

inline class Port(val value: Int) {
    fun validate() = value in 0..65535
}