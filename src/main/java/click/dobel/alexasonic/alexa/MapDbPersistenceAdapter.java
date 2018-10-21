package click.dobel.alexasonic.alexa;

import click.dobel.alexasonic.mapdb.DbFactory;
import com.amazon.ask.attributes.persistence.PersistenceAdapter;
import com.amazon.ask.exception.PersistenceException;
import com.amazon.ask.model.RequestEnvelope;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class MapDbPersistenceAdapter implements PersistenceAdapter {

  private final DB database;
  private final Function<RequestEnvelope, String> partitionKeyGenerator;
  private final String collectionName;
  private static final Serializer<String> keySerializer = Serializer.STRING;
  @SuppressWarnings("unchecked")
  private static final Serializer<Map<String, Object>> valueSerializer = Serializer.ELSA;

  public MapDbPersistenceAdapter(
    final DbFactory dbFactory,
    final String collectionName,
    final Function<RequestEnvelope, String> partitionKeyGenerator
  ) {
    this.database = dbFactory.createDb();
    this.collectionName = collectionName;
    this.partitionKeyGenerator = partitionKeyGenerator;
  }

  private HTreeMap<String, Map<String, Object>> createMap() {
    return database
      .hashMap(collectionName, keySerializer, valueSerializer)
      .createOrOpen();
  }

  @Override
  public Optional<Map<String, Object>> getAttributes(final RequestEnvelope envelope) throws PersistenceException {
    final String partitionKey = partitionKeyGenerator.apply(envelope);
    return Optional.ofNullable(createMap().get(partitionKey));
  }

  @Override
  public void saveAttributes(
    final RequestEnvelope envelope,
    final Map<String, Object> attributes
  ) throws PersistenceException {
    final String partitionKey = partitionKeyGenerator.apply(envelope);
    createMap().put(partitionKey, attributes);
    database.commit();
  }
}
