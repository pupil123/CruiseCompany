package domain.builder;

import domain.User;

/**
 {@code UserBuilder} responsible for building instance of entity class
 *
 */
public class UserBuilder {
    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder buildId(int userId) {
        this.user.setId(userId);
        return this;
    }

    public UserBuilder buildFirstName(String firstName) {
        this.user.setFirstName(firstName);
        return this;
    }

    public UserBuilder buildLastName(String lastName) {
        this.user.setLastName(lastName);
        return this;
    }

    public UserBuilder buildAdministrator(boolean administrator) {
        this.user.setAdministrator(administrator);
        return this;
    }

    public UserBuilder buildLogin(String login) {
        this.user.setLogin(login);
        return this;
    }

    public UserBuilder buildPassword(String password) {
        this.user.setPassword(password);
        return this;
    }

    public User build() {
        return this.user;
    }

}
