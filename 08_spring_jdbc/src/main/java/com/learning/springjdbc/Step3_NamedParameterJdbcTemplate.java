package com.learning.springjdbc;

import com.learning.springjdbc.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/*
 * Step 3: named parameters.
 *
 * Positional `?` placeholders are fine for two args. They become a
 * counting bug magnet at five:
 *
 *   jdbc.update(
 *      "UPDATE products SET name=?, price=?, stock=? WHERE id=?",
 *      name, price, stock, id);             // hope you got the order right
 *
 * NamedParameterJdbcTemplate uses :placeholders + a Map:
 *
 *   namedJdbc.update(
 *      "UPDATE products SET name=:name, price=:price, stock=:stock WHERE id=:id",
 *      params);
 *
 * Order no longer matters; the SQL itself is more readable.
 */
public class Step3_NamedParameterJdbcTemplate {

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {

            NamedParameterJdbcTemplate jdbc = ctx.getBean(NamedParameterJdbcTemplate.class);

            SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name",  "Mechanical Keyboard")
                .addValue("price", new java.math.BigDecimal("99.99"))   // tiny price tweak
                .addValue("id",    1L);

            int rows = jdbc.update(
                "UPDATE products SET name = :name, price = :price WHERE id = :id",
                params);

            System.out.println("Updated rows: " + rows);

            // SELECT with named params + extracting one column
            String name = jdbc.queryForObject(
                "SELECT name FROM products WHERE id = :id",
                new MapSqlParameterSource("id", 1L),
                String.class);
            System.out.println("Product 1 name: " + name);
        }
    }
}
