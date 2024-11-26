package helpers;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import models.ValueAndExpiry;

public final class DatastoreHelper {
    private static volatile DatastoreHelper instance;

    private final Map<String, ValueAndExpiry> datastore;

    private DatastoreHelper() {
        this.datastore = new ConcurrentHashMap<>();
    }

    public static DatastoreHelper getInstance() {
        DatastoreHelper existingInstance = instance;
        if (!Objects.isNull(existingInstance)) {
            return existingInstance;
        }
        synchronized (DatastoreHelper.class) {
            if (Objects.isNull(instance)) {
                instance = new DatastoreHelper();
            }
            return instance;
        }
    }

    public void put(final String key, final ValueAndExpiry value) {
        this.datastore.put(key, value);
    }

    public ValueAndExpiry get(final String key) {
        return this.datastore.get(key);
    }
}
