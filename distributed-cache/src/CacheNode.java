import java.util.HashMap;
import java.util.Map;

public class CacheNode<K, V> {
    private final String nodeId;
    private final int capacity;
    private final EvictionPolicy<K> evictionPolicy;
    private final Map<K, V> map = new HashMap<>();

    public CacheNode(String nodeId, int capacity, EvictionPolicy<K> evictionPolicy) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be > 0");
        }
        this.nodeId = nodeId;
        this.capacity = capacity;
        this.evictionPolicy = evictionPolicy;
    }

    public synchronized V get(K key) {
        V result = map.get(key);
        if (result != null) {
            evictionPolicy.onKeyAccess(key);
        }
        return result;
    }

    public synchronized void put(K key, V value) {
        if (map.containsKey(key)) {
            map.put(key, value);
            evictionPolicy.onKeyAccess(key);
            return;
        }

        // check capacity and evict one if needed
        if (map.size() >= capacity) {
            K removeKey = evictionPolicy.selectEvictionCandidate();
            if (removeKey != null) {
                map.remove(removeKey);
                evictionPolicy.onKeyRemove(removeKey);
            }
        }

        map.put(key, value);
        evictionPolicy.onKeyInsert(key);
    }

    public String getNodeId() {
        return nodeId;
    }
}
