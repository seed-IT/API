package eu.seed.it.serialization

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

class PropertyValueDeserializer : JsonDeserializer<UpdateSeedData>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): UpdateSeedData {
        p.nextToken()
        val key = p.text
        val keyName = key[0].toUpperCase() + key.toLowerCase().substring(1, key.length)
        p.nextToken()
        val value = p.text
        try {
            val seedProperty = SeedProperty.valueOf(keyName)
            return UpdateSeedData(seedProperty, value)
        } catch (e: Exception) {
            throw JsonParseException(p, "$key is not a valid SeedProperty")
        }
    }
}

@JsonDeserialize(using = PropertyValueDeserializer::class)
data class UpdateSeedData(val property: SeedProperty, val value: String)

enum class SeedProperty {
    Name, Description, Tips
}