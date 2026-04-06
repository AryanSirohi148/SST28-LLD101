public class Main8 {
    public static void main(String[] args) {
        Database<String, String> db = new InMemoryDatabase<>();
        db.put("A", "Alpha");
        db.put("B", "Beta");
        db.put("C", "Gamma");

        // setup cache with nodes and replication
        DistributedCache<String, String> cache = new DistributedCache<>(
                4,
                3,
                new ModuloDistributionStrategy<>(),
                db,
                new LRUEvictionPolicyFactory<>()
        );

        System.out.println("A -> " + cache.get("A")); // miss -> DB -> cache
        System.out.println("A -> " + cache.get("A")); // hit

        cache.put("D", "Delta");
        System.out.println("D -> " + cache.get("D"));

        // Keys X and Y are likely to map to the same node in small node counts,
        // helping demonstrate per-node LRU eviction behavior.
        cache.put("X", "X-ray");
        cache.put("Y", "Yankee");
        System.out.println("X -> " + cache.get("X"));
        System.out.println("Y -> " + cache.get("Y"));
    }
}
