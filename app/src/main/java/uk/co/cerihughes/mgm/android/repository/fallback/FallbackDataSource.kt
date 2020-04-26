package uk.co.cerihughes.mgm.android.repository.fallback

import uk.co.cerihughes.mgm.android.datasource.remote.generated.model.EventApiModel

interface FallbackDataSource {
    fun getFallbackData(): List<EventApiModel>
}
