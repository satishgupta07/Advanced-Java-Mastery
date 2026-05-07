package com.learning.hibernate;

import com.learning.hibernate.model.User;
import com.learning.hibernate.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/*
 * JPA Criteria API — type-safe queries built from Java code.
 *
 * Trade-off:
 *   - HQL (Step 4) is concise but a TYPO becomes a runtime error.
 *   - Criteria is verbose but the COMPILER catches mistakes;
 *     refactor-renaming a field renames the query too.
 *
 * Use Criteria when the WHERE clause is built dynamically — e.g. a
 * search form where each filter is optional. String concatenation of
 * HQL gets ugly fast; Criteria stays readable.
 */
public class Step5_Criteria {

    public static void main(String[] args) {
        // Imagine these came from a form: any subset may be set.
        String nameFilter  = "Alice";       // or null
        String emailFilter = null;          // or "@example.com"

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            // Build predicates only for filters that were provided
            List<Predicate> where = new ArrayList<>();
            if (nameFilter != null) {
                where.add(cb.like(root.get("name"), "%" + nameFilter + "%"));
            }
            if (emailFilter != null) {
                where.add(cb.like(root.get("email"), "%" + emailFilter + "%"));
            }

            cq.select(root);
            if (!where.isEmpty()) {
                cq.where(cb.and(where.toArray(new Predicate[0])));
            }
            cq.orderBy(cb.asc(root.get("id")));

            List<User> results = s.createQuery(cq).getResultList();
            System.out.println("Matched " + results.size() + " user(s):");
            results.forEach(System.out::println);

        } finally {
            HibernateUtil.shutdown();
        }
    }
}
