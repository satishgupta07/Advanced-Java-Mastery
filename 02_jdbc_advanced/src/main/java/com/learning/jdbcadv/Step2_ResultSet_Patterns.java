package com.learning.jdbcadv;

import com.learning.jdbcadv.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Mapping a ResultSet row to a domain object.
 *
 * The "fetch into a POJO" loop you write here by hand is exactly
 * what Spring's RowMapper<User> does in Phase 8, and what Hibernate
 * does for you automatically in Phase 5.
 *
 * Pattern:
 *     1. Run the query.
 *     2. Walk the cursor (while rs.next()).
 *     3. For each row, instantiate a User and populate it from columns.
 *     4. Add to a list.
 *     5. Return the list.
 */
public class Step2_ResultSet_Patterns {

    public static void main(String[] args) {
        for (User u : findAllUsers()) {
            System.out.println(u);
        }
    }

    static List<User> findAllUsers() {
        List<User> result = new ArrayList<>();
        String sql = "SELECT id, name, email FROM users ORDER BY id";

        try (Connection c = DriverManager.getConnection(
                Step1_PreparedStatement.URL,
                Step1_PreparedStatement.USER,
                Step1_PreparedStatement.PASS);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("findAllUsers failed: " + e.getMessage());
        }
        return result;
    }

    // Pulled out so each query reuses the same mapping logic.
    // This is exactly the body of a Spring RowMapper.mapRow().
    private static User mapRow(ResultSet rs) throws SQLException {
        return new User(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email")
        );
    }
}
