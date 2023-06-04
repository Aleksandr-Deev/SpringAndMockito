package org.example;

import java.util.*;

public class UserRepository {

    private final List<User> userList = new ArrayList<>();

    public User addUser(User user) {
        this.userList.add(user);
        return user;
    }

    public Optional<User> getUserLogin(String login) {
        return this.userList.stream()
                .filter(user -> user.getLogin().equals(login))
                .findAny();
    }

    public Optional<User> getUserLoginAndPassword(String login, String password) {
        return this.userList.stream()
                .filter(user -> user.getLogin().equals(login))
                .filter(user -> user.getPassword().equals(password))
                .findAny();

    }

    public Collection<User> getAllUser() {
        return Collections.unmodifiableCollection(userList);
    }
}
