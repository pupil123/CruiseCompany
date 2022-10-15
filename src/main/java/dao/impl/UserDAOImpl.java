package dao.impl;

import dao.CommonsOperable;
import dao.MySQLConnectorManager;
import dao.UserDAO;
import domain.User;
import filter.AuthenticationFilter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;

import static util.Constants.*;

/**
 * The {@code UserDaoImpl} class is a JDBC implementation
 * of {@code UserDao} interface
 */
public class UserDAOImpl implements UserDAO, CommonsOperable {

    /**
     * Receives User and saves it into DB
     *
     * @param user the instance of {@code User} entity class
     * @return int of the new user Id from DB.
     */
    @Override
    public int addNewUser(User user) throws SQLException {

        int saveId = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_ADD_NEW_USER, Statement.RETURN_GENERATED_KEYS)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setBoolean(3, user.isAdministrator());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    saveId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_PERSIST_USER);
        }
        return saveId;
    }

    /**
     * Receives User's login and password and checks if user with such data in DB exists
     *
     * @param login    the instance of {@code String} specifies user's login
     * @param password the instance of {@code String} specifies user's password
     * @return boolean of the verified user in DB.
     */
    @Override
    public boolean verificateUser(String login, String password) {

        int result = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_COUNT_USERS_WITH_LOGIN)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            result = resultSet.getInt(1);

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            e.printStackTrace();
            //   throw new SQLException(COULD_NOT_PERSIST_USER);
        }
        return result == 1;
    }

    /**
     * Receives User's login and password and checks if user with such data is admin in DB
     *
     * @param login    the instance of {@code String} specifies user's login
     * @param password the instance of {@code String} specifies user's password
     * @return boolean of the verified user in DB.
     */
    @Override
    public boolean isAdmin(String login, String password) {

        int result = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_IS_ADMIN_USER)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, login);

            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            result = resultSet.getInt(1);

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }

    /**
     * Receives User's login and password and searches for user's Id with such data in DB
     *
     * @param login    the instance of {@code String} specifies user's login
     * @param password the instance of {@code String} specifies user's password
     * @return int of the user's Id in DB.
     */
    @Override
    public int idByLogPas(String login, String password) {
        int result = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_GET_USER_ID_BY_LOG_PAS)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            result = resultSet.getInt(1);

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * Receives the id of User and deletes order from DB.
     *
     * @param userId instance of {@code int} Parameter specifies the User.
     */
    @Override
    public void deleteUserById(int userId) throws SQLException {
        deleteItemById(userId, SQL_DELETE_USER_BY_ID);


    }

    /**
     * Receives user and updates it in DB.
     *
     * @param user instance of {@code User} entity class.
     */
    @Override
    public void updateUser(User user) throws SQLException {
        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_UPDATE_USER)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setBoolean(3, user.isAdministrator());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setInt(6, user.getId());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(COULD_NOT_PERSIST_USER);
        }
    }

    /**
     * Receives the connection and returns all the users from DB.
     *
     * @param connection java.sql.Connection
     * @return ResultSet containing the data of all the Users from DB.
     */
    @Override
    public ResultSet getAllUsers(Connection connection) throws SQLException {
        return getAllItems(connection, SQL_GET_ALL_USERS);
    }

    /**
     * Responsible for getting the User with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param userId     the {@code int} parameter, specifies order.
     * @return ResultSet with all the data of User.
     */
    @Override
    public ResultSet getUserById(int userId, Connection connection) throws SQLException {
        return getItemById(connection, SQL_GET_USER_BY_ID, userId);
    }
}
