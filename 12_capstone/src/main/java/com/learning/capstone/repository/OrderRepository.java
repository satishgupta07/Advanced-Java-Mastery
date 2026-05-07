package com.learning.capstone.repository;

import com.learning.capstone.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /*
     * Phase 6's JOIN FETCH lesson, applied. Without this query, the
     * controller's call to order.getUser() / order.getProduct() inside
     * the JSON serializer would fire two extra SELECTs PER ORDER
     * (the N+1 problem). One @Query, no extra round-trips.
     */
    @Query("""
           SELECT DISTINCT o
           FROM   Order o
           JOIN   FETCH o.user
           JOIN   FETCH o.product
           ORDER BY o.id
           """)
    List<Order> findAllWithUserAndProduct();
}
