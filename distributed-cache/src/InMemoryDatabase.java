import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDatabase<K, V> implements Database<K, V> {
    private final Map<K, V> store = new ConcurrentHashMap<>();

    @Override
    public V get(K key) {
        return store.get(key);
    }

    @Override
    public void put(K key, V value) {
        store.put(key, value);
    }
}
