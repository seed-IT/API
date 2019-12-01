package eu.seed.it.server.modules

import com.fasterxml.jackson.databind.ObjectMapper
import eu.seed.it.database.Database
import eu.seed.it.kodein
import eu.seed.it.server.ServerModule
import eu.seed.it.utils.message
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import spark.kotlin.get

class UserModule : ServerModule {
    private val mapper: ObjectMapper by kodein.instance()
    private val database: Database by kodein.instance()

    private fun resp(id: Long): String {
        val objectNode = mapper.createObjectNode()
        objectNode.put("id", id)
        return objectNode.toString()
    }

    override fun run() {
        get("/user/:email") {
            val email = request.params(":email")
            if (email == null) {
                status(400)
                return@get message("Bad Request")
            }
            val userId = database.userId(email)
            if (userId == null) {
                status(404)
                return@get message("Not Found")
            }
            resp(userId)
        }

    }

}

val userModule = Kodein.Module("user") {
    bind<ServerModule>().inSet() with singleton { UserModule() }
}