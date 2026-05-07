package com.learning.springhib.service;

import com.learning.springhib.model.User;
import com.learning.springhib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * The service is where @Transactional belongs.
 *
 *   - One method = one unit of work = one transaction.
 *   - The same Hibernate Session covers both repo calls inside
 *     register(...), so the persist + the findAll see consistent state.
 *
 * Read-only flag is more than a hint:
 *   - skips dirty-checking on commit (faster)
 *   - some DBs use it to route to a read replica
 */
@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User register(String name, String email) {
        User u = new User(name, email);
        repository.save(u);
        return u;       // id is populated, the session will commit on return
    }

    @Transactional(readOnly = true)
    public List<User> listAll() {
        return repository.findAll();
    }

    @Transactional
    public void rename(Long id, String newName) {
        User u = repository.findById(id);
        if (u != null) {
            u.setName(newName);    // dirty checking → UPDATE on commit, no save() call
        }
    }
}
