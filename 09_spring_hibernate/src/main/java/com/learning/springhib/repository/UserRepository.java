package com.learning.springhib.repository;

import com.learning.springhib.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Two things that look small but matter:
 *
 *   1. We INJECT the SessionFactory — we don't build it.
 *      Spring is the owner; this repo is the user.
 *
 *   2. We call sf.getCurrentSession(), NOT openSession().
 *      Inside a @Transactional method, currentSession() returns
 *      the session bound to the active transaction. Calling
 *      openSession() instead would create a fresh session that
 *      isn't part of the tx — your saves wouldn't roll back, and
 *      the caller would have to close it.
 */
@Repository
public class UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(User u) {
        sessionFactory.getCurrentSession().persist(u);
    }

    public User findById(Long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    public List<User> findAll() {
        return sessionFactory.getCurrentSession()
            .createQuery("FROM User", User.class)
            .list();
    }

    public void delete(User u) {
        sessionFactory.getCurrentSession().remove(u);
    }
}
