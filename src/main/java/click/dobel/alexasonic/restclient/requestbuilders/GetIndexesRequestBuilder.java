package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.Indexes;

public class GetIndexesRequestBuilder extends AbstractSubsonicRequestBuilder<GetIndexesRequestBuilder, Indexes> {

  GetIndexesRequestBuilder() {
    super("getIndexes");
  }

  public GetIndexesRequestBuilder withMusicFolderId(final int musicFolderId) {
    return with(RequestParameters.PARAM_MUSIC_FOLDER_ID, musicFolderId);
  }

  public GetIndexesRequestBuilder ifModifiedSince(final long epochMillis) {
    return with(RequestParameters.PARAM_IF_MODIFIED_SINCE, epochMillis);
  }
}
