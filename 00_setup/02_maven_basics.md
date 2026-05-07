# Step 2 — Maven Basics

> *Maven solves: "how do I get all the JARs my project needs, in the right versions, on the classpath?"*

Before Maven, Java developers downloaded JAR files manually and copied them into a `lib/` folder. Maven replaces that with a single declarative file — `pom.xml` — that lists what you need; Maven downloads it.

---

## The mental model

```
You write:    pom.xml          (a list of what you need)
Maven reads:  pom.xml
Maven does:   download JARs from Maven Central → put them on classpath → compile → test → package
```

Everything Maven does is driven by `pom.xml`. Learn to read one and you can read every Java project on GitHub.

---

## Anatomy of `pom.xml`

```xml
<project>
    <!-- WHO this project is -->
    <groupId>com.learning</groupId>           <!-- like a package: company / org -->
    <artifactId>jdbc-basics</artifactId>      <!-- the project name -->
    <version>1.0.0</version>
    <packaging>jar</packaging>                <!-- jar | war | pom -->

    <!-- WHAT this project depends on -->
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.3.0</version>
        </dependency>
    </dependencies>
</project>
```

**Three coordinates** identify any Java library on the planet: `groupId` + `artifactId` + `version`. That triple is the address Maven uses to fetch the JAR.

---

## Commands you'll actually use

| Command | What it does |
|---------|-------------|
| `mvn clean` | Delete the `target/` folder |
| `mvn compile` | Compile `src/main/java` → `target/classes` |
| `mvn test` | Run unit tests in `src/test/java` |
| `mvn package` | Build a JAR/WAR into `target/` |
| `mvn install` | Package + copy to local Maven cache (`~/.m2/`) so other local projects can use it |
| `mvn clean package` | The everyday combo — wipe and rebuild |
| `mvn dependency:tree` | Show the full dependency graph (great for debugging conflicts) |

---

## Standard project layout

Maven has *opinions* about where files go. Follow the convention; you'll thank yourself.

```
project-root/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/         ← your .java source
│   │   └── resources/    ← config files (application.properties, hibernate.cfg.xml, etc.)
│   └── test/
│       ├── java/         ← test code
│       └── resources/    ← test config
└── target/               ← build output (gitignored)
```

---

## The Maven lifecycle, condensed

```
validate → compile → test → package → verify → install → deploy
```

Running any phase runs all preceding phases too. `mvn package` runs `validate`, `compile`, `test`, then `package`.

---

> Next: [03_database_setup.md](03_database_setup.md)
