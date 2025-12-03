package be.kdg.ipj3.platformbackend.games;

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
class GameIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class GameListIntegrationFlows {
        @Test
        public void getGames_should_return_200() throws Exception {
            mockMvc.perform(get("/api/games")).andExpect(status().isOk());
        }

    }
    @Nested
    class GameByIdIntegrationFlows{
        @Test
        public void getGameById_should_return_200_when_fired_with_valid_id() throws Exception {
            UUID gameId = UUID.fromString("11111111-1111-1111-1111-111111111111");
            mockMvc.perform(get("/api/games/{id}", gameId))
                    .andExpect(status().isOk());
        }

        @Test
        public void getGameById_should_return_404_when_fired_with_invalid_id() throws Exception {
            UUID gameId = UUID.fromString("22222222-2222-2222-2222-222222222222");
            mockMvc.perform(get("/api/games/{id}", gameId))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class AddGameFavoriteIntegrationFlows{
        @Test
        public void addFavorite_should_return_200_when_fired_with_valid_gameId()throws Exception{
            UUID gameId = UUID.fromString("11111111-1111-1111-1111-111111111111");
            UUID userId = UUID.randomUUID();
            mockMvc.perform(post("/api/games/favorite/{id}", gameId)
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
        public void addFavorite_should_return_404_when_fired_with_invalid_gameId()throws Exception{
            UUID gameId = UUID.fromString("22222222-2222-2222-2222-222222222222");
            UUID userId = UUID.randomUUID();
            mockMvc.perform(post("/api/games/favorite/{id}", gameId)
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
        public void addFavorite_should_return_401_when_fired_without_a_valid_jwt_token()throws Exception{
            UUID gameId = UUID.fromString("22222222-2222-2222-2222-222222222222");
            mockMvc.perform(post("/api/games/favorite/{id}", gameId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class RemoveGameFavoriteIntegrationFlows{
        @Test
        public void addFavorite_should_return_200_when_fired_with_valid_gameId()throws Exception{
            UUID gameId = UUID.fromString("11111111-1111-1111-1111-111111111111");
            UUID userId = UUID.randomUUID();
            mockMvc.perform(delete("/api/games/favorite/{id}", gameId)
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
        public void addFavorite_should_return_404_when_fired_with_invalid_gameId()throws Exception{
            UUID gameId = UUID.fromString("22222222-2222-2222-2222-222222222222");
            UUID userId = UUID.randomUUID();
            mockMvc.perform(delete("/api/games/favorite/{id}", gameId)
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
        public void removeFavorite_should_return_401_when_fired_without_a_valid_jwt_token()throws Exception{
            UUID gameId = UUID.fromString("22222222-2222-2222-2222-222222222222");
            mockMvc.perform(delete("/api/games/favorite/{id}", gameId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }
    }


}
