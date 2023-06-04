package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class UserService  {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String login, String password) throws Exception {
        User user = new User(login, password);

        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            throw new IllegalAccessException("User login and password should be defined");
        }

        boolean userExist = this.userRepository
                .getAllUser()
                .stream()
                .anyMatch(t -> t.equals(user));
        if (userExist) {
            throw new UserNonUniqueException("User already exist");
        }

        this.userRepository.addUser(user);
    }

    public List<String> getAllUserLogin() {
        return this.userRepository
                .getAllUser()
                .stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
    }

}
