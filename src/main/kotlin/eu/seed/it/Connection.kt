package eu.seed.it

data class Connection(val address: String, val port: Int) {
    fun validate() = port in 0..65535

    override fun toString(): String {
        return "$address:$port"
    }
}