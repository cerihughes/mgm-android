package uk.co.cerihughes.mgm.android.repository

import uk.co.cerihughes.mgm.android.datasource.remote.generated.api.DefaultApi
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.fallback.FallbackDataSource

class RepositoryImpl(
    private val fallbackDataSource: FallbackDataSource,
) : Repository {

    private val api = DefaultApi()

    private var cachedEvents: List<Event>? = null

    override suspend fun getEvents(): List<Event> {
        return cachedEvents ?: loadEvents()
    }

    private suspend fun loadEvents(): List<Event> {
        return try {
            val events = api.events().map { it.toDataModel() }
            cachedEvents = events
            events
        } catch (e: Exception) {
            fallbackDataSource.getFallbackData().map { it.toDataModel() }
        }
    }
}
