package service;

import domain.User;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code UserService} interface is responsible for processing business logic
 * with {@code User} entity class
 */
public interface UserService {
    /**
     * Receives user and saves it into DB
     *
     * @param user the instance of {@code User} entity class
     * @return int of the new user Id from DB.
     */
    int addNewUser(User user) throws SQLException;

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

    /**
     * Responsible for getting the list of all Users from DB.
     *
     * @return {@code List<User>} the list of all Users from DB.
     */
    List<User> getAllUsers() throws SQLException;;

    /**
     * Responsible for deleting specified User from DB.
     *
     * @param userId instance of {@code int} parameter specifies User.
     */
    void deleteUserById(int userId) throws SQLException;

    /**
     * Responsible for updating User in DB
     *
     * @param user instance of {@code User} entity class .
     */
    void updateUser(User user) throws SQLException;

    /**
     * Responsible for getting User instance with specified id.
     *
     * @param userId instance of {@code int} parameter specifies User.
     * @return {@code User} instance with specified id.
     */
    User getUserById(int userId) throws SQLException;


}
