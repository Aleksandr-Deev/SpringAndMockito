package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private UserRepository userRepository;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        user1 = new User("user1", "password1");
        user2 = new User("user2", "password2");

        userRepository = new UserRepository();

        userRepository.addUser(user1);
        userRepository.addUser(user2);
    }

    @Test
    public void testGetAllUsersEmpty() {
        assertFalse(userRepository.getAllUser().isEmpty());
    }

    @Test
    public void testGetAllUsersWithInitialData() {

        assertEquals(2, userRepository.getAllUser().size());
        assertTrue(userRepository.getAllUser().contains(user1));
        assertTrue(userRepository.getAllUser().contains(user2));
    }

    @Test
    public void testFindUserByLoginExists() {

        Optional<User> foundUser = userRepository.getUserLogin("user1");
        assertTrue(foundUser.isPresent());
        assertEquals(user1, foundUser.get());
    }

    @Test
    public void testFindUserByLoginNotExists() {
        Optional<User> foundUser = userRepository.getUserLogin("jojrfhjhrfj");
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testFindUserByLoginAndPasswordExists() {

        Optional<User> foundUser = userRepository.getUserLoginAndPassword("user1", "password1");
        assertTrue(foundUser.isPresent());
        assertEquals(user1, foundUser.get());
    }

    @Test
    public void testFindUserByLoginAndPasswordWrongPassword() {

        Optional<User> foundUser = userRepository.getUserLoginAndPassword("user1", "wrorlahker");
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testFindUserByLoginAndPasswordWrongLogin() {

        Optional<User> foundUser = userRepository.getUserLoginAndPassword("wronglogin", "password1");
        assertFalse(foundUser.isPresent());
    }

}
