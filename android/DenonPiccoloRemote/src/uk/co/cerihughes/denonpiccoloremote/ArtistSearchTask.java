package uk.co.cerihughes.denonpiccoloremote;

import java.util.ArrayList;

import uk.co.cerihughes.denonpiccoloremote.core.dao.DaoException;
import uk.co.cerihughes.denonpiccoloremote.core.dao.impl.SpotifyServiceDao;
import uk.co.cerihughes.denonpiccoloremote.core.model.Artist;
import android.os.AsyncTask;

public class ArtistSearchTask extends AsyncTask<String, Integer, Iterable<Artist>>
{
	@Override
	protected Iterable<Artist> doInBackground(String... params)
	{
		ArrayList<Artist> results = new ArrayList<Artist>();
		SpotifyServiceDao dao = new SpotifyServiceDao();
		for (String predicate : params)
		{
			try
			{
				results.addAll(dao.searchArtists(predicate));
			}
			catch (DaoException e)
			{
				e.printStackTrace();
			}
		}
		return results;
	}

	@Override
	protected void onPostExecute(Iterable<Artist> result)
	{
	}
}
