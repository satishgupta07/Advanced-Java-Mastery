package com.learning.boot.service;

import com.learning.boot.model.User;
import com.learning.boot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
 * Same shape as Phase 9's UserService — only the repository
 * implementation has shrunk from "hand-written CRUD" to "interface
 * extending JpaRepository". Everything else (annotations, transaction
 * semantics) is identical.
 */
@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {     // single constructor → @Autowired implicit
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    @Transactional
    public User register(User u) {
        return repo.save(u);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
