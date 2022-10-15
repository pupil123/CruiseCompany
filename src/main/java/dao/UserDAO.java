package dao;

import domain.Cruise;
import domain.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code UserDAO} interface is responsible for
 * connecting {@code User} entity class to DB
 */
public interface UserDAO {
    /**
     * Receives User and saves it into DB
     *
     * @param user the instance of {@code User} entity class
     * @return int of the new user Id from DB.
     */
    int addNewUser(User user) throws SQLException;

    /**
     * Receives the id of User and deletes order from DB.
     *
     * @param userId instance of {@code int} Parameter specifies the User.
     */
    void deleteUserById(int userId)throws SQLException;

    /**
     * Receives user and updates it in DB.
     *
     * @param user instance of {@code User} entity class.
     */
    void updateUser(User user)throws SQLException;

    /**
     * Receives the connection and returns all the users from DB.
     *
     * @param connection java.sql.Connection
     * @return ResultSet containing the data of all the Users from DB.
     */
    ResultSet getAllUsers(Connection connection) throws SQLException;

    /**
     * Responsible for getting the User with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param userId    the {@code int} parameter, specifies order.
     * @return ResultSet with all the data of User.
     */
    ResultSet getUserById(int userId, Connection connection) throws SQLException;

    /**
     * Receives User's login and password and checks if user with such data in DB exists
     *
     * @param login the instance of {@code String} specifies user's login
     * @param password the instance of {@code String} specifies user's password
     * @return boolean of the verified user in DB.
     */
    boolean verificateUser(String login, String password) ;

    /**
     * Receives User's login and password and checks if user with such data is admin in DB
     *
     * @param login the instance of {@code String} specifies user's login
     * @param password the instance of {@code String} specifies user's password
     * @return boolean of the verified user in DB.
     */
    boolean isAdmin(String login, String password) ;

    /**
     * Receives User's login and password and searches for user's Id with such data in DB
     *
     * @param login the instance of {@code String} specifies user's login
     * @param password the instance of {@code String} specifies user's password
     * @return int of the user's Id in DB.
     */
    int idByLogPas (String login, String password) ;

}
