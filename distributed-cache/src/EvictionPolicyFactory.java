public interface EvictionPolicyFactory<K> {
    EvictionPolicy<K> create();
}
