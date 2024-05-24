package org.apollo.template.Database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JDBCTest {

    private static JDBC jdbc;

    @BeforeAll
    static void setUp() {
        // Create an instance of JDBC before running the tests
        jdbc = JDBC.get();
    }

    @Test
    void getConnection() {
        // Check if the getConnection method returns a non-null connection object
        Connection connection = jdbc.getConnection();
        assertNotNull(connection);
        try {
            // Check if the connection is valid by calling isValid method
            assertTrue(connection.isValid(5));
        } catch (SQLException e) {
            fail("SQLException occurred while checking connection validity: " + e.getMessage());
        }
    }

    @AfterAll
    static void tearDown() {
        // Close the database connection after running the tests
        jdbc.databaseClose();
    }
}