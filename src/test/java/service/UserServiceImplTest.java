package service;

import dao.impl.UserDAOImpl;
import domain.*;
import domain.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Test;
import service.impl.UserServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImplTest {

    private UserService userService = new UserServiceImpl(new UserDAOImpl());

    @Test
    public void shouldAddNewUser() throws SQLException {
        User user = new UserBuilder()
                .buildPassword("123456")
                .buildLogin("a")
                .buildFirstName("A")
                .buildLastName("V")
                .build();
        int userId = userService.addNewUser(user);
        user.setId(userId);
        User user1 = userService.getUserById(userId);

        Assert.assertEquals(user1.getId(), userId);
        Assert.assertEquals(user1, user);

        userService.deleteUserById(userId);
    }

    @Test
    public void shouldUpdateUser() throws SQLException, IOException {

        User user1 = saveUser();

        user1.setFirstName("MyFirstName");

        userService.updateUser(user1);

        User user3 = userService.getUserById(user1.getId());

        Assert.assertEquals(user3.getFirstName(), user1.getFirstName());
        userService.deleteUserById(user1.getId());
    }

    @Test
    public void shouldDeleteUser() throws SQLException, IOException {

        List<User> allUsers1 = userService.getAllUsers();

        User user1 = saveUser();

        userService.deleteUserById(user1.getId());

        List<User> allUsers2 = userService.getAllUsers();

        Assert.assertEquals(allUsers1, allUsers2);
    }

    @Test
    public void shouldGetAllUsers() throws SQLException, IOException {
        List<User> allUsers1 = userService.getAllUsers();
        User user1 = saveUser();
        List<User> allUsers2 = userService.getAllUsers();

        Assert.assertEquals(allUsers1.size() + 1, allUsers2.size());
        userService.deleteUserById(user1.getId());
    }

    @Test
    public void shouldBeVerifiableUser() throws SQLException, IOException {
        User user1 = saveUser();
        boolean b = userService.verificateUser(user1.getLogin(), user1.getPassword());
        Assert.assertTrue(b);
        userService.deleteUserById(user1.getId());
    }

    @Test
    public void shouldBeNotAdmin() throws SQLException, IOException {
        User user1 = saveUser();
        boolean b = userService.isAdmin(user1.getLogin(), user1.getPassword());
        Assert.assertFalse(b);
        userService.deleteUserById(user1.getId());
    }

    @Test
    public void shouldGetIdByLogPas() throws SQLException, IOException {
        User user1 = saveUser();
        userService.isAdmin(user1.getLogin(), user1.getPassword());
        Assert.assertEquals(user1.getId(), userService.idByLogPas(user1.getLogin(), user1.getPassword()));
        userService.deleteUserById(user1.getId());
    }

    private User saveUser() throws IOException, SQLException {
        User user = new UserBuilder()
                .buildPassword("123456")
                .buildLogin("a")
                .buildFirstName("A")
                .buildLastName("V")
                .build();

        int userId = userService.addNewUser(user);

        user.setId(userId);

        return user;

    }
}
