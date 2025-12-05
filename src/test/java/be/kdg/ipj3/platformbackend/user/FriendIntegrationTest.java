package be.kdg.ipj3.platformbackend.user;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
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

    @Nested
    class GetFriendListFlows {
        @Test
        public void getFriendList_should_return_200_when_authenticated() throws Exception {
            //Arrange
            UUID userId = UUID.randomUUID();
            //Act
            //Assert
            mockMvc.perform(get("/api/user/friends")
                            .with(jwt()
                                    .jwt(jwt -> jwt
                                            .subject(userId.toString())
                                            .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                            .claim(StandardClaimNames.FAMILY_NAME, "user")
                                            .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                    )
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        }

        @Test
        public void getFriendList_should_return_401_when_not_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(get("/api/user/friends")).andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class GetFriendRequestListFlows {
        @Test
        public void getFriendList_should_return_200_when_authenticated() throws Exception {
            //Arrange
            UUID userId = UUID.randomUUID();
            //Act
            //Assert
            mockMvc.perform(get("/api/user/friends/requests")
                            .with(jwt()
                                    .jwt(jwt -> jwt
                                            .subject(userId.toString())
                                            .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                            .claim(StandardClaimNames.FAMILY_NAME, "user")
                                            .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                    )
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        }

        @Test
        public void getFriendList_should_return_401_when_not_authenticated() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(get("/api/user/friends/requests")).andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class AddFriendRequestFlows {
        @Test
        public void addFriendRequest_should_return_200_when_authenticated_with_valid_ids() throws Exception {
            //Arrange
            UUID userId = UUID.fromString("11111111-1111-1111-1234-111111111111");
            UUID friendId = UUID.fromString("11111111-1111-1111-aaaa-111111111111");
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId)
                            .with(jwt()
                                    .jwt(jwt -> jwt
                                            .subject(userId.toString())
                                            .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                            .claim(StandardClaimNames.FAMILY_NAME, "user")
                                            .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                    )
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        }

        @Test
        public void addFriendRequest_should_return_401_when_not_authenticated() throws Exception {
            //Arrange
            UUID friendId = UUID.fromString("11111111-1111-1111-aaaa-111111111111");
            //Act
            //Assert
            mockMvc.perform(get("/api/user/friends" + friendId)).andExpect(status().isUnauthorized());
        }

        @Test
        public void addFriendRequest_should_return_404_whith_invalid_user_id() throws Exception {
            //Arrange
            UUID userId = UUID.randomUUID();
            UUID friendId = UUID.fromString("11111111-1111-1111-aaaa-111111111111");
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId)
                            .with(jwt()
                                    .jwt(jwt -> jwt
                                            .subject(userId.toString())
                                            .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                            .claim(StandardClaimNames.FAMILY_NAME, "user")
                                            .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                    )
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        public void addFriendRequest_should_return_404_whith_invalid_friend_id() throws Exception {
            //Arrange
            UUID userId = UUID.fromString("11111111-1111-1111-1234-111111111111");
            UUID friendId = UUID.randomUUID();
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId)
                            .with(jwt()
                                    .jwt(jwt -> jwt
                                            .subject(userId.toString())
                                            .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                            .claim(StandardClaimNames.FAMILY_NAME, "user")
                                            .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                    )
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        public void addFriendRequest_shouldReturn409_WhenFiredWithAlreadyExistingParams() throws Exception {
            //Arrange
            UUID userId = UUID.fromString("11111111-1111-1111-aabb-111111111111");
            UUID friendId = UUID.fromString("11111111-1111-1111-aaaa-111111111111");
            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId)
                            .with(jwt()
                                    .jwt(jwt -> jwt
                                            .subject(userId.toString())
                                            .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                            .claim(StandardClaimNames.FAMILY_NAME, "user")
                                            .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                    )
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
            mockMvc.perform(patch("/api/user/friends/" + friendId)
                            .with(jwt()
                                    .jwt(jwt -> jwt
                                            .subject(userId.toString())
                                            .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                            .claim(StandardClaimNames.FAMILY_NAME, "user")
                                            .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                    )
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isConflict());
        }

        @Test
        public void addFriendRequest_from_users_in_both_directions_should_return_200() throws Exception {
            // Arrange
            UUID userId = UUID.fromString("11111111-1111-1111-1234-111111111111");
            UUID friendId = UUID.fromString("11111111-1111-1111-aabb-111111111111");

            //Act
            //Assert
            mockMvc.perform(patch("/api/user/friends/" + friendId)
                            .with(jwt()
                                    .jwt(jwt -> jwt
                                            .subject(userId.toString())
                                            .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                            .claim(StandardClaimNames.FAMILY_NAME, "user")
                                            .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                    )
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            mockMvc.perform(patch("/api/user/friends/" + userId)
                            .with(jwt()
                                    .jwt(jwt -> jwt
                                            .subject(friendId.toString())
                                            .claim(StandardClaimNames.GIVEN_NAME, "test_user_2")
                                            .claim(StandardClaimNames.FAMILY_NAME, "user2")
                                            .claim(StandardClaimNames.EMAIL, "test_user_2@test.be")
                                    )
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }


        @Nested
        class AcceptFriendRequestFlows {
            @Test
            public void accept_friend_request_should_return_200_when_there_is_one_open_and_user_is_authenticated() throws Exception {
                //Arrange
                UUID userId = UUID.fromString("11111111-1111-1111-1234-111111111111");
                UUID friendId = UUID.fromString("11111111-1111-1111-aaab-111111111111");
                //Act
                //Assert
                mockMvc.perform(patch("/api/user/friends/" + friendId + "/accept")
                                .with(jwt()
                                        .jwt(jwt -> jwt
                                                .subject(userId.toString())
                                                .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                                .claim(StandardClaimNames.FAMILY_NAME, "user")
                                                .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                        )
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

            }

            @Test
            public void accept_friend_request_should_return_404_when_there_is_none_open_and_user_is_authenticated() throws Exception {
                //Arrange
                UUID userId = UUID.fromString("11111111-1111-1111-1234-111111111111");
                UUID friendId = UUID.fromString("11111111-1111-1111-aaaa-111111111111");
                //Act
                //Assert
                mockMvc.perform(patch("/api/user/friends/" + friendId + "/accept")
                                .with(jwt()
                                        .jwt(jwt -> jwt
                                                .subject(userId.toString())
                                                .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                                .claim(StandardClaimNames.FAMILY_NAME, "user")
                                                .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                        )
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());

            }

            @Test
            public void acceptFriendRequest_should_return_401_when_not_authenticated() throws Exception {
                //Arrange
                UUID friendId = UUID.fromString("11111111-1111-1111-aaaa-111111111111");
                //Act
                //Assert
                mockMvc.perform(get("/api/user/friends" + friendId+"/accept")).andExpect(status().isUnauthorized());
            }
        }

        @Nested
        class GetFriendListFlows {
            @Test
            public void removeFriend_should_return_404_when_provided_userId_doesnt_exist() throws Exception {
                //Arrange
                UUID userId = UUID.randomUUID();
                UUID friendId = UUID.fromString("11111111-1111-1111-aaac-111111111111");

                //Act
                //Assert
                mockMvc.perform(delete("/api/user/friends/"+friendId)
                                .with(jwt()
                                        .jwt(jwt -> jwt
                                                .subject(userId.toString())
                                                .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                                .claim(StandardClaimNames.FAMILY_NAME, "user")
                                                .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                        )
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());

            }

            @Test
            public void removeFriend_should_return_40_when_called_on_a_non_friend() throws Exception {
                //Arrange
                UUID userId = UUID.fromString("11111111-1111-1111-1234-111111111111");
                UUID friendId = UUID.fromString("11111111-1111-1111-aaad-111111111111");

                //Act
                //Assert
                mockMvc.perform(delete("/api/user/friends/"+friendId)
                                .with(jwt()
                                        .jwt(jwt -> jwt
                                                .subject(userId.toString())
                                                .claim(StandardClaimNames.GIVEN_NAME, "test_user")
                                                .claim(StandardClaimNames.FAMILY_NAME, "user")
                                                .claim(StandardClaimNames.EMAIL, "test_user@test.be")
                                        )
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());

            }

            @Test
            public void removeFriend_should_return_401_when_authenticated_and_called_with_actual_friend() throws Exception {
                //Arrange
                UUID friendId = UUID.fromString("11111111-1111-1111-aaac-111111111111");
                //Act
                //Assert
                mockMvc.perform(delete("/api/user/friends/"+friendId)).andExpect(status().isUnauthorized());
            }
        }

    }


}
