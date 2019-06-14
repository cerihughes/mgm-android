package uk.co.cerihughes.mgm.android.repository.local

import android.content.Context
import android.preference.PreferenceManager
import uk.co.cerihughes.mgm.android.R
import uk.co.cerihughes.mgm.android.model.Event
import uk.co.cerihughes.mgm.android.repository.GsonFactory

class LocalDataSourceImpl(context: Context) : LocalDataSource {

    companion object {
        val PREFERENCES_KEY = "MGM_REMOTE_DATA"
    }

    private val fallbackData: String
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val gson = GsonFactory.createGson()

    init {
        fallbackData = context.resources.openRawResource(R.raw.mgm).bufferedReader().use { it.readText() }
    }

    override fun getEvents(): List<Event> {
        val data = getLocalData() ?: return emptyList()
        return gson.fromJson(data, Array<Event>::class.java).toList()
    }

    override fun setEvents(events: List<Event>) {
        val json = gson.toJson(events)
        persistRemoteData(json)
    }

    private fun getLocalData(): String? {
        return sharedPreferences.getString(PREFERENCES_KEY, fallbackData)
    }

    private fun persistRemoteData(remoteData: String): Boolean {
        val editor = sharedPreferences.edit()
        editor.putString(PREFERENCES_KEY, remoteData)
        return editor.commit()
    }
}