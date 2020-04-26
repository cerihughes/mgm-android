package uk.co.cerihughes.mgm.android.repository.fallback

import android.content.Context
import uk.co.cerihughes.mgm.android.R
import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel
import uk.co.cerihughes.mgm.android.repository.GsonFactory

class FallbackDataSourceImpl(context: Context) : FallbackDataSource {

    private val fallbackData: String
    private val gson = GsonFactory.createGson()

    init {
        fallbackData = context.resources.openRawResource(R.raw.mgm).bufferedReader().use { it.readText() }
    }

    override fun getFallbackData(): List<EventApiModel> {
        return gson.fromJson(fallbackData, Array<EventApiModel>::class.java).toList()
    }
}
