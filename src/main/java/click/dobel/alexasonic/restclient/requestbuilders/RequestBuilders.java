package click.dobel.alexasonic.restclient.requestbuilders;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public class RequestBuilders {

    public static PingRequestBuilder ping(final SubsonicCredentials configuration) {
        return new PingRequestBuilder(configuration);
    }

    public static GetLicenseRequestBuilder getLicense(final SubsonicCredentials configuration) {
        return new GetLicenseRequestBuilder(configuration);
    }

    public static GetMusicFoldersRequestBuilder getMusicFolders(final SubsonicCredentials configuration) {
        return new GetMusicFoldersRequestBuilder(configuration);
    }

    public static GetIndexesRequestBuilder getIndexes(final SubsonicCredentials configuration) {
        return new GetIndexesRequestBuilder(configuration);
    }

    public static GetArtistsRequestBuilder getArtists(final SubsonicCredentials configuration) {
        return new GetArtistsRequestBuilder(configuration);
    }

    public static GetRandomSongsRequestBuilder getRandomSongs(final SubsonicCredentials configuration) {
        return new GetRandomSongsRequestBuilder(configuration);
    }

    public static StreamRequestBuilder stream(final SubsonicCredentials configuration) {
        return new StreamRequestBuilder(configuration);
    }

}
