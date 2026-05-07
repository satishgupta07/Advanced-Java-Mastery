package com.learning.jdbcadv.dao;

import com.learning.jdbcadv.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/*
 * UserDAO — the only place in this project that knows about the users table.
 *
 *   - Domain code calls findById(7L) and gets back a User.
 *   - It never sees Connection, PreparedStatement, or ResultSet.
 *
 * This separation is the seed of every ORM that follows:
 *
 *   Hibernate's    session.get(User.class, 7L)
 *   Spring Data's  userRepository.findById(7L)
 *
 * ...are both DAOs whose method bodies are generated for you.
 *
 * Credentials are loaded from db.properties on the classpath, so this
 * class has no hard-coded URL/user/password.
 */
public class UserDAO {

    private final String url;
    private final String user;
    private final String password;

    public UserDAO() {
        Properties props = new Properties();
        try (InputStream in = ClassLoader.getSystemResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException("db.properties not found on classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load db.properties", e);
        }
        this.url      = props.getProperty("db.url");
        this.user     = props.getProperty("db.user");
        this.password = props.getProperty("db.password");
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // ---- CREATE ----------------------------------------------------------
    public User save(User u) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection c = openConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.executeUpdate();

            // Fetch the auto-generated id and write it back into the POJO
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    u.setId(keys.getLong(1));
                }
            }
            return u;
        } catch (SQLException e) {
            throw new RuntimeException("save(User) failed", e);
        }
    }

    // ---- READ ------------------------------------------------------------
    public Optional<User> findById(long id) {
        String sql = "SELECT id, name, email FROM users WHERE id = ?";
        try (Connection c = openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("findById failed", e);
        }
    }

    public List<User> findAll() {
        List<User> out = new ArrayList<>();
        String sql = "SELECT id, name, email FROM users ORDER BY id";
        try (Connection c = openConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(mapRow(rs));
            return out;
        } catch (SQLException e) {
            throw new RuntimeException("findAll failed", e);
        }
    }

    // ---- UPDATE ----------------------------------------------------------
    public int update(User u) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (Connection c = openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setLong(3, u.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("update failed", e);
        }
    }

    // ---- DELETE ----------------------------------------------------------
    public int deleteById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection c = openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("deleteById failed", e);
        }
    }

    // ---- mapping helper --------------------------------------------------
    private static User mapRow(ResultSet rs) throws SQLException {
        return new User(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email")
        );
    }
}
