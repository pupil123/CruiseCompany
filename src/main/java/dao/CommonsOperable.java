package dao;

import java.sql.*;

/**
 * The {@code CommonsOperable} interface is responsible for
 * connecting {@code Cruise} entity class to DB
 */
public interface CommonsOperable {

    /**
     * Responsible for deleting Item from DB
     *
     * @param itemId instance of {@code int} Parameter specifies Item.
     * @param query  instance of {@code String} Parameter specifies query
     * @throws SQLException when persist DB fails
     */
    default void deleteItemById(int itemId, String query) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, itemId);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);
        }
    }

    /**
     * Responsible for getting all Items from DB
     *
     * @param connection java.sql.Connection
     * @param query      instance of {@code String} Parameter specifies query
     * @return ResultSet with data of all items.
     * @throws SQLException when persist DB fails
     */
    default ResultSet getAllItems(Connection connection, String query) throws SQLException {

        Statement statement = connection.createStatement();

        return statement.executeQuery(query);
    }

    /**
     * Responsible for getting Item with specified itemId from DB
     *
     * @param connection java.sql.Connection
     * @param query      instance of {@code String} Parameter specifies query.
     * @param itemId     instance of {@code int} Parameter specifies Item.
     * @return ResultSet with data of itemId.
     * @throws SQLException when persist DB fails
     */
    default ResultSet getItemById(Connection connection, String query, int itemId) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, itemId);

        return statement.executeQuery();
    }
}
