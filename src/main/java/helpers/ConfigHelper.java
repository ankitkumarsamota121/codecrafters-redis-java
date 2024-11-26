package helpers;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class ConfigHelper {
    private static volatile ConfigHelper instance;

    private final Map<String, String> config;

    private ConfigHelper() {
        this.config = new ConcurrentHashMap<>();
    }

    public static ConfigHelper getInstance() {
        ConfigHelper existingInstance = instance;
        if (!Objects.isNull(existingInstance)) {
            return existingInstance;
        }
        synchronized (ConfigHelper.class) {
            if (Objects.isNull(instance)) {
                instance = new ConfigHelper();
            }
            return instance;
        }
    }

    public void put(final String key, final String value) {
        this.config.put(key, value);
    }

    public String get(final String key) {
        return this.config.get(key);
    }
}
