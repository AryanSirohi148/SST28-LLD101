import java.util.List;

public class ModuloDistributionStrategy<K, V> implements DistributionStrategy<K, V> {
    @Override
    public CacheNode<K, V> selectNode(K key, List<CacheNode<K, V>> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            throw new IllegalArgumentException("At least one cache node is required.");
        }

        int index = Math.floorMod(key.hashCode(), nodes.size());
        return nodes.get(index);
    }
}
