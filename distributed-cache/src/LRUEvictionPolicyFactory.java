public class LRUEvictionPolicyFactory<K> implements EvictionPolicyFactory<K> {
    @Override
    public EvictionPolicy<K> create() {
        return new LRUEvictionPolicy<>();
    }
}
