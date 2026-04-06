import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final Set<K> accessOrder = new LinkedHashSet<>();

    @Override
    public void onKeyAccess(K key) {
        accessOrder.remove(key);
        accessOrder.add(key);
    }

    @Override
    public void onKeyInsert(K key) {
        accessOrder.remove(key);
        accessOrder.add(key);
    }

    @Override
    public void onKeyRemove(K key) {
        accessOrder.remove(key);
    }

    @Override
    public K selectEvictionCandidate() {
        Iterator<K> iterator = accessOrder.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        return iterator.next();
    }
}
