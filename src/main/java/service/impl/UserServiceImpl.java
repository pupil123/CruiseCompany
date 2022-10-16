package service.impl;

import dao.MySQLConnectorManager;
import dao.UserDAO;
import domain.User;
import domain.builder.UserBuilder;
import service.UserService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserServiceImpl} class is a service implementation
 * of {@code UserService} interface
 */
public class UserServiceImpl implements UserService {
    private final UserDAO USER_DAO;

    public UserServiceImpl(UserDAO USER_DAO) {
        this.USER_DAO = USER_DAO;
    }

    /**
     * Receives user and saves it into DB
     *
     * @param user the instance of {@code User} entity class
     * @return int of the new user Id from DB.
     */
    @Override
    public int addNewUser(User user) throws SQLException {
        return USER_DAO.addNewUser(user);
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
        return USER_DAO.verificateUser(login, password);
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
        return USER_DAO.isAdmin(login, password);
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
        return USER_DAO.idByLogPas(login, password);
    }

    /**
     * Responsible for getting the list of all Users from DB.
     *
     * @return {@code List<User>} the list of all Users from DB.
     */
    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getAllUsers(connection);

            users = getUsersFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {

        }
        return users;

    }

    /**
     * Responsible for building the list of Users instance from the list of data encapsulated in ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getAllUsers()} method.
     * @return {@code List<User>} from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private List<User> getUsersFromResultSet(ResultSet resultSet) throws SQLException {

        List<User> users = new ArrayList<>();

        while (resultSet.next()) {

            User user = new UserBuilder()
                    .buildId(resultSet.getInt(1))
                    .buildFirstName(resultSet.getString(2))
                    .buildLastName(resultSet.getString(3))
                    .buildAdministrator(resultSet.getBoolean(4))
                    .buildLogin(resultSet.getString(5))
                    .buildPassword(resultSet.getString(6))
                    .build();

            users.add(user);
        }
        return users;
    }

    /**
     * Responsible for deleting specified User from DB.
     *
     * @param userId instance of {@code int} parameter specifies User.
     * @throws SQLException when persist DB fails
     */
    @Override
    public void deleteUserById(int userId) throws SQLException {

        USER_DAO.deleteUserById(userId);
    }

    /**
     * Responsible for updating User in DB
     *
     * @param user instance of {@code User} entity class .
     * @throws SQLException when persist DB fails
     */
    @Override
    public void updateUser(User user) throws SQLException {
        USER_DAO.updateUser(user);
    }

    /**
     * Responsible for getting User instance with specified id.
     *
     * @param userId instance of {@code int} parameter specifies User.
     * @return {@code User} instance with specified id.
     */
    @Override
    public User getUserById(int userId) {

        User user = new User();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getUserById(userId, connection);

            user = getUserFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

        }
        return user;
    }

    /**
     * Responsible for building User instance from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getOrderById()} method instance
     *                  encapsulating data of User.
     * @return {@code User} from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {

        User user = new User();

        while (resultSet.next()) {

            user = new UserBuilder()
                    .buildId(resultSet.getInt(1))
                    .buildFirstName(resultSet.getString(2))
                    .buildLastName(resultSet.getString(3))
                    .buildAdministrator(resultSet.getBoolean(4))
                    .buildLogin(resultSet.getString(5))
                    .buildPassword(resultSet.getString(6))
                    .build();
        }
        return user;
    }
}
