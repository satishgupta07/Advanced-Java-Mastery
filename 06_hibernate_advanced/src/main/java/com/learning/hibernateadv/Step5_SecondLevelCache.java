package com.learning.hibernateadv;

import com.learning.hibernateadv.model.Product;
import com.learning.hibernateadv.util.HibernateUtil;
import org.hibernate.Session;

/*
 * Step 5: second-level cache.
 *
 * Hibernate has TWO caches:
 *
 *   1st-level cache   per-Session, automatic.
 *                     Same query in the same Session → no SQL after the first hit.
 *                     This is why Step 3's N+1 isn't WORSE than it is.
 *
 *   2nd-level cache   shared across Sessions, opt-in.
 *                     Activated by:
 *                       - cfg flag: hibernate.cache.use_second_level_cache=true
 *                       - @Cacheable on the entity
 *                       - @Cache(usage = ...) for concurrency strategy
 *
 * When to enable 2L cache:
 *   - read-mostly reference data (countries, currencies, product catalog)
 *   - high read traffic on a small dataset
 *
 * When NOT to:
 *   - write-heavy entities (cache invalidation costs > savings)
 *   - data that must always reflect the latest write
 *
 * This file:
 *   - Opens Session A, loads Product 1, closes A.   → 1 SELECT
 *   - Opens Session B, loads Product 1, closes B.   → 0 SELECTs (cache hit)
 *
 * Without the 2nd-level cache, Session B would have made a fresh SELECT.
 */
public class Step5_SecondLevelCache {

    public static void main(String[] args) {

        // First Session — populates the cache.
        try (Session a = HibernateUtil.getSessionFactory().openSession()) {
            Product p = a.get(Product.class, 1L);
            System.out.println("Session A loaded: " + p);
        }

        // Second Session — should hit the cache, no SQL fired.
        try (Session b = HibernateUtil.getSessionFactory().openSession()) {
            Product p = b.get(Product.class, 1L);
            System.out.println("Session B loaded: " + p + "  (no SELECT expected)");
        }

        // Statistics confirm the hit/miss counts
        var stats = HibernateUtil.getSessionFactory().getStatistics();
        System.out.println("2L cache hits  : " + stats.getSecondLevelCacheHitCount());
        System.out.println("2L cache misses: " + stats.getSecondLevelCacheMissCount());

        HibernateUtil.shutdown();
    }
}
