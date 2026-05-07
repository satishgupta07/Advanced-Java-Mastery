package com.learning.boot.repository;

import com.learning.boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
 * The whole DAO, replaced by an INTERFACE.
 *
 * Spring Data inspects this interface at startup and generates a proxy
 * that implements:
 *
 *   save / saveAll / findById / findAll / count / deleteById / delete / existsById ...
 *
 * Plus query methods derived from method names (no SQL, no HQL):
 *
 *   findByEmail(String email)                  → WHERE email = ?
 *   findByNameContainingIgnoreCase(String s)   → WHERE LOWER(name) LIKE LOWER(%?%)
 *
 * Compare every prior phase's UserDAO/UserRepository — Spring Data
 * gets you the same surface area for ZERO lines of implementation.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByNameContainingIgnoreCase(String fragment);
}
