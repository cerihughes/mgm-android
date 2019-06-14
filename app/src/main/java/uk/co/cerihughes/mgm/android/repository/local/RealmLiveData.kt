package uk.co.cerihughes.mgm.android.repository.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults
import uk.co.cerihughes.mgm.android.repository.asRealmList

class RealmLiveData<T : RealmModel>(val realmResults: RealmResults<T>) : LiveData<RealmResults<T>>() {

    private val listener = RealmChangeListener<RealmResults<T>> { results -> value = results }

    override fun onActive() {
        realmResults.addChangeListener(listener)
    }

    override fun onInactive() {
        realmResults.removeChangeListener(listener)
    }
}

fun <T : RealmModel> RealmResults<T>.asLiveData(): LiveData<List<T>> {
    return RealmLiveData<T>(this).map { it.asRealmList() }
}