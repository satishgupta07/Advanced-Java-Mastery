package com.learning.jdbcadv;

import com.learning.jdbcadv.dao.UserDAO;
import com.learning.jdbcadv.model.User;

/*
 * Driver class — exercises the DAO without ever touching JDBC directly.
 *
 * Notice what is NOT in this file:
 *   - Connection
 *   - PreparedStatement
 *   - ResultSet
 *   - SQLException
 *   - any string of SQL
 *
 * That is the point of the DAO: the caller writes pure domain code.
 * From here, swapping the storage layer (Hibernate, Spring Data, an
 * in-memory map for tests) only changes UserDAO — never this file.
 */
public class Step5_DAO_Pattern {

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // CREATE — id is filled in by the DAO from generated keys
        User created = dao.save(new User("Eve Martinez", "eve@example.com"));
        System.out.println("Saved : " + created);

        // READ
        dao.findAll().forEach(u -> System.out.println("Row   : " + u));

        // UPDATE
        created.setName("Eve M.");
        dao.update(created);
        dao.findById(created.getId())
           .ifPresent(u -> System.out.println("After : " + u));

        // DELETE — clean up so the script is idempotent
        dao.deleteById(created.getId());
        System.out.println("Deleted user " + created.getId());
    }
}
