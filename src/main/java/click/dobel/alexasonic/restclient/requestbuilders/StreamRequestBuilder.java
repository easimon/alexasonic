package click.dobel.alexasonic.restclient.requestbuilders;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public class StreamRequestBuilder extends AbstractSubsonicRequestBuilder<StreamRequestBuilder, byte[]> {

    public StreamRequestBuilder(final SubsonicCredentials configuration) {
        super(configuration, "stream");
    }

    /**
     * A string which uniquely identifies the file to stream. Obtained by calls to
     * getMusicDirectory.
     * 
     * @param id
     *            id of the file to stream.
     * @return this
     */
    public StreamRequestBuilder withId(final String id) {
        return with(PARAM_ID, id);
    }

    /**
     * (Since 1.2.0) If specified, the server will attempt to limit the bitrate to
     * this value, in kilobits per second. If set to zero, no limit is imposed.
     * 
     * @param maxBitRate
     *            the maximum bit rate, in kbps.
     * @return this
     */
    public StreamRequestBuilder withMaxBitRate(final int maxBitRate) {
        return with(PARAM_MAX_BIT_RATE, maxBitRate);
    }

    /**
     * (Since 1.6.0) Specifies the preferred target format (e.g., "mp3" or "flv") in
     * case there are multiple applicable transcodings. Starting with 1.9.0 you can
     * use the special value "raw" to disable transcoding.
     * 
     * @param format
     *            the format
     * @return this
     */
    public StreamRequestBuilder withFormat(final String format) {
        return with(PARAM_FORMAT, format);
    }

    /**
     * Only applicable to video streaming. If specified, start streaming at the
     * given offset (in seconds) into the video. Typically used to implement video
     * skipping.
     * 
     * @param seconds
     *            the offset in seconds.
     * @return this
     */

    public StreamRequestBuilder withTimeOffset(final int seconds) {
        return with(PARAM_TIME_OFFSET, seconds);
    }

    /**
     * (Since 1.6.0) Only applicable to video streaming. Requested video size
     * specified as WxH, for instance "640x480".
     * 
     * @param pixelSizeXY
     *            the pixel size, e.g. "640x480"
     * @return this
     */
    public StreamRequestBuilder withSize(final String pixelSizeXY) {
        return with(PARAM_SIZE, pixelSizeXY);
    }

    /**
     * (Since 1.8.0). If set to "true", the Content-Length HTTP header will be set
     * to an estimated value for transcoded or downsampled media.
     * 
     * @param estimate
     *            do estimate the content length.
     * @return this
     */
    public StreamRequestBuilder withEstimateContentLength(final boolean estimate) {
        return with(PARAM_ESTIMATE_CONTENT_LENGTH, estimate);
    }

    /**
     * (Since 1.14.0) Only applicable to video streaming. Subsonic can optimize
     * videos for streaming by converting them to MP4. If a conversion exists for
     * the video in question, then setting this parameter to "true" will cause the
     * converted video to be returned instead of the original.
     * 
     * @param converted
     *            convert the video stream to MP4?
     * @return this
     */
    public StreamRequestBuilder withConverted(final boolean converted) {
        return with(PARAM_CONVERTED, converted);
    }

}
