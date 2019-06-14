package uk.co.cerihughes.mgm.android.repository

import com.google.gson.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

class GsonFactory {
    companion object {
        fun createGson(): Gson {
            return GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, LocalDateConverter())
                .setDateFormat("dd/MM/yyyy")
                .create()
        }
    }
}

class LocalDateConverter : JsonDeserializer<LocalDate> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalDate {
        return FORMATTER.parse(json.asString, LocalDate.FROM)
    }

    companion object {
        var FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    }
}