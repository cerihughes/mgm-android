package uk.co.cerihughes.mgm.android.repository

import uk.co.cerihughes.mgm.android.model.Event

interface Repository {
    suspend fun getEvents(): List<Event>
}
