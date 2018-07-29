package click.dobel.alexasonic.restclient;

import click.dobel.alexasonic.configuration.SubsonicCredentials;
import click.dobel.alexasonic.repository.SubsonicCredentialsRepository;
import click.dobel.alexasonic.restclient.requestbuilders.AbstractSubsonicRequestBuilder;
import click.dobel.alexasonic.restclient.responseconverters.FlatteningSubsonicResponseConverter;
import click.dobel.alexasonic.restclient.responseconverters.SubsonicResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.subsonic.restapi.Response;
import org.subsonic.restapi.ResponseStatus;

import java.net.URI;

@Component
public class SubsonicRestClient {

    private final RestTemplate restTemplate;
    private final SubsonicCredentialsRepository credentialsRepository;

    @Autowired
    public SubsonicRestClient(final RestTemplateBuilder restTemplateBuilder,
                              final SubsonicCredentialsRepository credentialsRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.credentialsRepository = credentialsRepository;
    }

    /* TODO: Find out how to unwrap root element configuratively. */
    private Response requestAndUnwrapResponse(final URI uri) {
        final SubsonicResponseWrapper wrapper = restTemplate.getForObject(uri, SubsonicResponseWrapper.class);
        return wrapper.getResponse();
    }

    protected Response doRequest(final URI uri) {
        final Response response = requestAndUnwrapResponse(uri);
        if (!ResponseStatus.OK.equals(response.getStatus())) {
            throw new SubsonicRestServiceFailure(response.getStatus(), response.getError());
        }
        return response;
    }

    public <B extends AbstractSubsonicRequestBuilder<B, T>, T> T execute(
        final AbstractSubsonicRequestBuilder<B, T> builder,
        final SubsonicResponseConverter<T> converter,
        final SubsonicCredentials credentials
    ) {
        final URI uri = builder.getUri(credentials);
        final Response response = doRequest(uri);
        return converter.convert(response);
    }

    public <B extends AbstractSubsonicRequestBuilder<B, T>, T, F> F executeAndFlatten(
        final AbstractSubsonicRequestBuilder<B, T> builder,
        final FlatteningSubsonicResponseConverter<T, F> converter,
        final SubsonicCredentials credentials
    ) {
        final URI uri = builder.getUri(credentials);
        final Response response = doRequest(uri);
        return converter.convertAndFlatten(response);
    }
}
