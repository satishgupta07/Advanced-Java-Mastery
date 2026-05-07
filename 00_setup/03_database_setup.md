# Step 3 — Database Setup

> *One schema, used by every later phase. Set it up once.*

We'll use **MySQL 8.x**. PostgreSQL works just as well — only the JDBC driver coordinates change.

---

## Install MySQL

**Windows:** download the MySQL Installer, choose "Server only" or "Developer Default", and remember the **root password** you set during install. That's the only setup gotcha.

After install, verify:

```powershell
mysql --version
```

---

## Create the learning database

Open a MySQL shell:

```powershell
mysql -u root -p
```

Then run:

```sql
CREATE DATABASE learning_db;
CREATE USER 'learner'@'localhost' IDENTIFIED BY 'learner_pw';
GRANT ALL PRIVILEGES ON learning_db.* TO 'learner'@'localhost';
FLUSH PRIVILEGES;
```

A dedicated user (`learner`) keeps your root credentials out of every later `pom.xml` and config file.

---

## Load the sample schema

From this folder:

```powershell
mysql -u learner -p learning_db < 04_sample_schema.sql
```

The script creates three tables — `users`, `products`, `orders` — and seeds a few rows so you have something to query.

---

## Verify

```powershell
mysql -u learner -p learning_db -e "SELECT * FROM users;"
```

If you see three rows back, you're done with setup.

---

## The connection string you'll see everywhere

```
jdbc:mysql://localhost:3306/learning_db
```

| Part | Meaning |
|------|---------|
| `jdbc:` | The protocol — JDBC URL |
| `mysql:` | The sub-protocol — tells JDBC which driver to load |
| `//localhost:3306` | Host + port |
| `/learning_db` | The database name |

Memorize the structure. Every later phase reuses it.

---

> Next: [04_sample_schema.sql](04_sample_schema.sql)
