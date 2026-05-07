package com.learning.spring;

import com.learning.spring.repository.UserRepository;
import com.learning.spring.service.UserService;

/*
 * Step 1: BEFORE Spring — manual wiring.
 *
 * This file works. It's also the very thing Spring exists to eliminate.
 *
 * Notice:
 *   - main() knows about EVERY layer (UserRepository AND UserService).
 *   - Adding a new dependency (say, a logger or a cache) requires
 *     edits in every place that constructs the graph.
 *   - To unit-test UserService, we'd construct a real UserRepository,
 *     or hand-roll a fake. There's no easy substitution.
 *
 * Each later step replaces this main() with a Spring container that
 * builds the same graph from a declaration.
 */
public class Step1_NewEverywhere {

    public static void main(String[] args) {
        // Manually wire the graph: leaf first, then up the chain.
        UserRepository repository = new UserRepository();
        UserService    service    = new UserService(repository);

        service.register("Dave");
        System.out.println("Users: " + service.listAll());
    }
}
