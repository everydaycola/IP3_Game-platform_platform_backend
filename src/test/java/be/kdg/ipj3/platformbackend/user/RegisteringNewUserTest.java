package be.kdg.ipj3.platformbackend.user;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.application.UserService;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class RegisteringNewUserTest {

    @Mock
    PlatformUserRepository platformUserRepository;

    @InjectMocks
    UserService userService;

    @Nested
    class SuccesFlows {
        @Test
        void addUser_should_return_user_when_user_id_is_provided() {
            //Arrange
            UserId userId = new UserId(UUID.randomUUID());
            String userName1 = "TestUser1";
            PlatformUser mockUser = new PlatformUser(userId,userName1,"", new ArrayList<>());
            Mockito.when(platformUserRepository.createUser(Mockito.any(PlatformUser.class)))
                    .thenReturn(mockUser);
            // Act
            PlatformUser result = userService.addUser(userId,userName1);
            // Assert
            assertThat(result).isNotNull();
            assertThat(result.getUserId()).isEqualTo(userId);
        }
    }

}
