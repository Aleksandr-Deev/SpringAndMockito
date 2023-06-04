package org.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void whenTheUserIsInTheRepositoryTheUserIsLoginWillBeReturnedCorrect() {
        when(userRepository.getAllUser())
                .thenReturn(List.of(new User("test1", "testpass1")
                        ,new User("test2", "testpass2")));
        assertThat(userService.getAllUserLogin()).toString();
    }

    @Test
    void whenTheRepositoryIsEmptyThenShouldReturnZero() {
        when(userRepository.getAllUser())
                .thenReturn(List.of());
        assertThat(userService.getAllUserLogin()).isEmpty();
    }

    @Test
    void whenCorrectUserIsAddedThenAddUserIsCalledFromRepo() throws Exception {
        when(userRepository.getAllUser()).thenReturn(List.of());
        when(userRepository.addUser(any(User.class))).thenReturn(null);
        userService.addUser("login", "password");
        verify(userRepository).addUser(any());
    }

    @Test
    void whenInvalidUserIsPassedThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.addUser("", ""))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessage("User login and password should be defined");
        verify(userRepository, new NoInteractions()).getAllUser();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void whenExistingUserIsPassedThenServiceThrowException() {
        when(userRepository.getAllUser()).thenReturn(List.of(new User("login", "password")));
        assertThatThrownBy(() -> userService.addUser("login", "password"))
                .isInstanceOf(UserNonUniqueException.class)
                .hasMessage("User already exist");
    }
}
