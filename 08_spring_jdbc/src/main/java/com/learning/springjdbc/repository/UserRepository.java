package com.learning.springjdbc.repository;

import com.learning.springjdbc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * Compare with Phase 2's UserDAO:
 *   - No try-with-resources.
 *   - No SQLException — exceptions are unchecked DataAccessExceptions.
 *   - No PreparedStatement plumbing.
 *
 * Just SQL + a RowMapper.
 */
@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // The RowMapper — used by every query method below. ResultSet → User.
    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getString("email")
    );

    public List<User> findAll() {
        return jdbc.query("SELECT id, name, email FROM users ORDER BY id", ROW_MAPPER);
    }

    public Optional<User> findById(long id) {
        try {
            // queryForObject expects EXACTLY one row; throws if 0 or >1.
            User u = jdbc.queryForObject(
                "SELECT id, name, email FROM users WHERE id = ?", ROW_MAPPER, id);
            return Optional.ofNullable(u);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int save(User u) {
        return jdbc.update(
            "INSERT INTO users (name, email) VALUES (?, ?)",
            u.getName(), u.getEmail());
    }

    public int update(User u) {
        return jdbc.update(
            "UPDATE users SET name = ?, email = ? WHERE id = ?",
            u.getName(), u.getEmail(), u.getId());
    }

    public int deleteById(long id) {
        return jdbc.update("DELETE FROM users WHERE id = ?", id);
    }
}
