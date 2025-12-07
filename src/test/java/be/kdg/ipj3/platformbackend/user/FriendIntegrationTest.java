package be.kdg.ipj3.platformbackend.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FriendIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID userId;
    private UUID friendId;
    private UUID friendId2;

    @BeforeEach
    void setUp() {
        userId = UUID.fromString("11111111-1111-1111-1234-111111111111");
        friendId = UUID.fromString("11111111-1111-1111-aaaa-111111111111");
        friendId2 = UUID.fromString("11111111-1111-1111-aabb-111111111111");
    }

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor authJwt(UUID id, String givenName, String familyName, String email) {
        return jwt().jwt(jwt -> jwt
                .subject(id.toString())
                .claim(StandardClaimNames.GIVEN_NAME, givenName)
                .claim(StandardClaimNames.FAMILY_NAME, familyName)
                .claim(StandardClaimNames.EMAIL, email)
        );
    }

    @Nested
    class GetFriendListFlows {
        @Test
        void getFriendList_should_return_200_when_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(get("/api/user/friends")
                            .with(authJwt(userId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void getFriendList_should_return_401_when_not_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(get("/api/user/friends"))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class GetFriendRequestListFlows {
        @Test
        void getFriendRequestList_should_return_200_when_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(get("/api/user/friends/requests")
                            .with(authJwt(userId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void getFriendRequestList_should_return_401_when_not_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(get("/api/user/friends/requests"))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class AddFriendRequestFlows {
        @Test
        void addFriendRequest_should_return_200_when_authenticated_with_valid_ids() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId)
                            .with(authJwt(userId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void addFriendRequest_should_return_401_when_not_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void addFriendRequest_should_return_404_with_invalid_user_id() throws Exception {
            //Arrange
            UUID invalidUserId = UUID.randomUUID();
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId)
                            .with(authJwt(invalidUserId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void addFriendRequest_should_return_404_with_invalid_friend_id() throws Exception {
            //Arrange
            UUID invalidFriendId = UUID.randomUUID();

            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + invalidFriendId)
                            .with(authJwt(userId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void addFriendRequest_shouldReturn409_WhenAlreadyExists() throws Exception {
            //Arrange
            //Act
            //Assert
            //Check on conflict
            mockMvc.perform(patch("/api/user/friends/" + friendId2)
                            .with(authJwt(userId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isConflict());
        }

        @Test
        void addFriendRequest_from_users_in_both_directions_should_return_200() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + userId)
                            .with(authJwt(friendId2, "test_user_2", "user2", "test_user_2@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class AcceptFriendRequestFlows {
        @Test
        void accept_friend_request_should_return_200_when_open_and_authenticated() throws Exception {
            //Arrange
            mockMvc.perform(patch("/api/user/friends/" + friendId2)
                            .with(authJwt(userId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + userId + "/accept")
                            .with(authJwt(friendId2, "friend_user", "friend", "friend_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void accept_friend_request_should_return_404_when_none_open_and_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId + "/accept")
                            .with(authJwt(userId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void accept_friend_request_should_return_401_when_not_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId + "/accept"))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class DenyFriendRequestFlows {

        @Test
        void deny_friend_request_should_return_200_when_open_and_authenticated() throws Exception {
            //Todo: implement this test correctly.
        }

        @Test
        void deny_friend_request_should_return_404_when_none_open_and_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId + "/deny")
                            .with(authJwt(userId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void deny_friend_request_should_return_401_when_not_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId + "/deny"))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class RemoveFriendFlows {
        @Test
        void removeFriend_should_return_404_when_user_doesnt_exist() throws Exception {
            //Arrange
            UUID invalidUserId = UUID.randomUUID();
            //Act
            //Assert
            mockMvc.perform(delete("/api/user/friends/" + friendId2)
                            .with(authJwt(invalidUserId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void removeFriend_should_return_404_when_non_friend() throws Exception {
            //Arrange
            UUID nonFriendId = UUID.fromString("11111111-1111-1111-aaad-111111111111");
            //Act
            //Assert
            mockMvc.perform(delete("/api/user/friends/" + nonFriendId)
                            .with(authJwt(userId, "test_user", "user", "test_user@test.be"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void removeFriend_should_return_401_when_not_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(delete("/api/user/friends/" + friendId))
                    .andExpect(status().isUnauthorized());
        }
    }

}




