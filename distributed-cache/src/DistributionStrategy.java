import java.util.List;

public interface DistributionStrategy<K, V> {
    CacheNode<K, V> selectNode(K key, List<CacheNode<K, V>> nodes);
}
