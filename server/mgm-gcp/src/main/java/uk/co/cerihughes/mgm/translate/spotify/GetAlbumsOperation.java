package uk.co.cerihughes.mgm.translate.spotify;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.requests.data.albums.GetSeveralAlbumsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetAlbumsOperation {

    public List<Album> execute(SpotifyApi spotifyApi, List<String> albumIds) throws IOException {
        final ArrayList<String> input = new ArrayList(albumIds);
        final ArrayList<Album> output = new ArrayList<>(input.size());

        while (input.size() > 0) {
            final int start = 0;
            final int end = input.size() < 20 ? input.size() : 20;

            final List<String> range = input.subList(start, end);
            String[] array = range.toArray(new String[range.size()]);

            final Album[] results = executeBatch(spotifyApi, array);
            output.addAll(Arrays.asList(results));

            range.clear();
        }

        return output;
    }

    private Album[] executeBatch(SpotifyApi spotifyApi, String... albumIds) throws IOException {
        try {
            final GetSeveralAlbumsRequest getSeveralAlbumsRequest = spotifyApi.getSeveralAlbums(albumIds)
                    .market(CountryCode.GB)
                    .build();
            return getSeveralAlbumsRequest.execute();
        } catch (SpotifyWebApiException e) {
            throw new IOException(e);
        }
    }
}