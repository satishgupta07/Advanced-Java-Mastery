package com.learning.capstone.repository;

import com.learning.capstone.model.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /*
     * Pessimistic write lock for the order flow.
     *
     * Two concurrent placeOrder() calls for the same product would
     * otherwise both read stock=5, both decide "yes, fits", and both
     * commit — overselling by one. PESSIMISTIC_WRITE acquires a row
     * lock so only one tx can read+update stock at a time.
     *
     * In a real system you'd compare alternatives (optimistic locking
     * with @Version, atomic UPDATE ... WHERE stock >= ?, etc.). The
     * point here is to show JPA gives you a knob.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findById(Long id);
}
