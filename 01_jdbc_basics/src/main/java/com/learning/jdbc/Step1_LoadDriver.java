package com.learning.jdbc;

/*
 * Step 1 of JDBC: Load the driver.
 *
 * The driver is the vendor-specific implementation of the JDBC API.
 * For MySQL, that class lives in mysql-connector-j and is named:
 *     com.mysql.cj.jdbc.Driver
 *
 * Historically, you had to call Class.forName(...) to force the JVM
 * to load the driver class, which would register itself with
 * DriverManager via a static block.
 *
 * Since JDBC 4.0 (Java 6+), this step is OPTIONAL.
 * The Service Provider Interface (SPI) auto-discovers any driver
 * found on the classpath via META-INF/services/java.sql.Driver.
 *
 * We still cover Class.forName() because:
 *   - You'll see it in older code and tutorials
 *   - It demonstrates how the registration works under the hood
 *   - It throws ClassNotFoundException, which is a useful sanity check
 *     ("is the driver JAR even on my classpath?")
 */
public class Step1_LoadDriver {

    public static void main(String[] args) {
        try {
            // Force the JVM to load the MySQL driver class.
            // Inside that class's static block, it registers itself with DriverManager.
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            // If you see this, the mysql-connector-j JAR is not on the classpath.
            // Check pom.xml or run: mvn dependency:tree
            System.err.println("Driver class not found. Is mysql-connector-j on the classpath?");
            e.printStackTrace();
        }
    }
}
