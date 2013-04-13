package uk.co.cerihughes.denon.core.dao;

import java.util.List;

import uk.co.cerihughes.denon.core.model.Album;
import uk.co.cerihughes.denon.core.model.Artist;
import uk.co.cerihughes.denon.core.model.Playlist;
import uk.co.cerihughes.denon.core.model.Track;

public interface IFiniteServiceDao extends InfiniteServiceDao
{

	List<Playlist> allPlaylists() throws DaoException;

	List<Artist> allArtists() throws DaoException;

	List<Album> allAlbums() throws DaoException;

	List<Track> allTracks() throws DaoException;

}