-- Sample schema used by every later phase of Advanced-Java-Mastery.
-- Three tables that mirror a tiny e-commerce domain: users, products, orders.
-- Same schema, reshaped through JDBC, Hibernate, Spring, and Spring Boot.

DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

-- ----------------------------------------------------------------------------
-- users — people who place orders
-- ----------------------------------------------------------------------------
CREATE TABLE users (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ----------------------------------------------------------------------------
-- products — items that can be ordered
-- ----------------------------------------------------------------------------
CREATE TABLE products (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150)   NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    stock       INT            NOT NULL DEFAULT 0
);

-- ----------------------------------------------------------------------------
-- orders — links a user to a product (a tiny join table on purpose)
-- ----------------------------------------------------------------------------
CREATE TABLE orders (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT      NOT NULL,
    product_id  BIGINT      NOT NULL,
    quantity    INT         NOT NULL,
    ordered_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_user    FOREIGN KEY (user_id)    REFERENCES users(id),
    CONSTRAINT fk_orders_product FOREIGN KEY (product_id) REFERENCES products(id)
);

-- ----------------------------------------------------------------------------
-- Seed data so queries return something on day one
-- ----------------------------------------------------------------------------
INSERT INTO users (name, email) VALUES
    ('Alice Johnson', 'alice@example.com'),
    ('Bob Smith',     'bob@example.com'),
    ('Carol Davis',   'carol@example.com');

INSERT INTO products (name, price, stock) VALUES
    ('Mechanical Keyboard', 89.99, 50),
    ('Wireless Mouse',      29.99, 120),
    ('27-inch Monitor',    349.99, 15);

INSERT INTO orders (user_id, product_id, quantity) VALUES
    (1, 1, 1),
    (1, 2, 2),
    (2, 3, 1);
