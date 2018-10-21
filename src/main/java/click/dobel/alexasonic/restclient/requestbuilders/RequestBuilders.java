package click.dobel.alexasonic.restclient.requestbuilders;

public final class RequestBuilders {

  private RequestBuilders() {
  }

  public static GetArtistsRequestBuilder getArtists() {
    return new GetArtistsRequestBuilder();
  }

  public static GetIndexesRequestBuilder getIndexes() {
    return new GetIndexesRequestBuilder();
  }

  public static GetLicenseRequestBuilder getLicense() {
    return new GetLicenseRequestBuilder();
  }

  public static GetMusicFoldersRequestBuilder getMusicFolders() {
    return new GetMusicFoldersRequestBuilder();
  }

  public static GetRandomSongsRequestBuilder getRandomSongs() {
    return new GetRandomSongsRequestBuilder();
  }

  public static GetSimilarSongsRequestBuilder getSimilarSongs() {
    return new GetSimilarSongsRequestBuilder();
  }

  public static GetSimilarSongs2RequestBuilder getSimilarSongs2() {
    return new GetSimilarSongs2RequestBuilder();
  }

  public static PingRequestBuilder ping() {
    return new PingRequestBuilder();
  }

  public static StreamRequestBuilder stream() {
    return new StreamRequestBuilder();
  }
}
