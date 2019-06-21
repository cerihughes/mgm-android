package uk.co.cerihughes.mgm.android.repository.local

import io.realm.Realm
import uk.co.cerihughes.mgm.android.model.Event

class LocalDataSourceImpl(private val realm: Realm) : LocalDataSource {

    override fun getEvents(): List<Event> {
        return realm.where(Event::class.java).findAll()
    }

    override fun addEvents(events: Collection<Event>) {
        realm.executeTransactionAsync { bgRealm ->
            for (event in events) {
                bgRealm.insertOrUpdate(event)
            }
        }
    }
}