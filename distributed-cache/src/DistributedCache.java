import java.util.ArrayList;
import java.util.List;

public class DistributedCache<K, V> {
    private final List<CacheNode<K, V>> nodes;
    private final DistributionStrategy<K, V> distributionStrategy;
    private final Database<K, V> database;

    public DistributedCache(
            int numberOfNodes,
            int perNodeCapacity,
            DistributionStrategy<K, V> distributionStrategy,
            Database<K, V> database,
            EvictionPolicyFactory<K> evictionPolicyFactory
    ) {
        if (numberOfNodes <= 0) {
            throw new IllegalArgumentException("numberOfNodes must be > 0");
        }

        this.distributionStrategy = distributionStrategy;
        this.database = database;
        this.nodes = new ArrayList<>();

        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new CacheNode<>("node-" + i, perNodeCapacity, evictionPolicyFactory.create()));
        }
    }

    public V get(K key) {
        CacheNode<K, V> node = distributionStrategy.selectNode(key, nodes);
        V value = node.get(key);

        if (value != null) {
            return value;
        }

        V dbValue = database.get(key);
        if (dbValue != null) {
            node.put(key, dbValue);
        }
        return dbValue;
    }

    public void put(K key, V value) {
        
        database.put(key, value);

        CacheNode<K, V> node = distributionStrategy.selectNode(key, nodes);
        node.put(key, value);
    }
}
