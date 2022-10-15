package dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The {@code MySQLConnectorManager} class is responsible for creation and managing connection to DB.
 */
public class MySQLConnectorManager {

    private static final Logger LOGGER = LogManager.getLogger(MySQLConnectorManager.class);

    private static BasicDataSource ds = new BasicDataSource();

    static {
        //ds.setUrl("jdbc:mysql://localhost:3306/cruise2?serverTimezone=Europe/Kiev");
        ds.setUrl("jdbc:mysql://localhost:3306/cruise2?serverTimezone=Europe/Kiev&characterEncoding=UTF-8");
       // ds.setUrl("jdbc:mysql://localhost:3306/cruise_test?serverTimezone=Europe/Kiev");
        ds.setUsername("root");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setPassword("pupil111");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
    }

    /**
     * Gets connection java.sql.Connection from connection pool ({@code DataSource} class)
     *
     * @return opened connection
     */
    public static synchronized Connection getConnection() {
        Connection connection = null;
        try {
            connection = ds.getConnection();

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());
        }
        return connection;
    }

    /**
     * Receives connection and closes it
     *
     * @param connection java.sql.Connection
     */
    public static void closeConnection(Connection connection) {
        try {
            connection.close();

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Receives connection and starts transaction by setting autocommit to false
     *
     * @param connection java.sql.Connection
     */
    public static void startTransaction(Connection connection) {
        try {

            connection.setAutoCommit(false);

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Receives connection and commits transaction
     *
     * @param connection java.sql.Connection
     */
    public static void commitTransaction(Connection connection) {

        try {
            connection.commit();
        } catch (SQLException e) {

            LOGGER.error(e.getMessage());
            rollbackTransaction(connection);
        }
    }

    /**
     * Receives connection and rollbacks transaction
     *
     * @param connection java.sql.Connection
     */
    public static void rollbackTransaction(Connection connection) {
        try {
            connection.rollback();

        } catch (SQLException e) {

            LOGGER.error(e.getMessage());
        }
    }

}
