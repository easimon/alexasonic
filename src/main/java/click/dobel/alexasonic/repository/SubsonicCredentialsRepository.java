package click.dobel.alexasonic.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

@Repository
public class SubsonicCredentialsRepository {

    private final SubsonicCredentials subsonicCredentials;

    @Autowired
    public SubsonicCredentialsRepository(final SubsonicCredentials subsonicCredentials) {
        this.subsonicCredentials = subsonicCredentials;
    }

    /* TODO: one configuration per user */
    public SubsonicCredentials getCredentialsForUser(final String userId) {
        return subsonicCredentials;
    }

}
