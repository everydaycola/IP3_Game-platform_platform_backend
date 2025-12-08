package be.kdg.ipj3.platformbackend.user;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.user.application.FriendService;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserFriendRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class FriendRequestListTest {

    @Mock
    PlatformUserFriendRepository platformUserFriendRepository;

    @InjectMocks
    FriendService friendService;

    @Nested
    class SuccesFlows {
        @Test
        void getFriendRequestList_should_return_expected() {
            //Arrange
            UserId userId = new UserId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
            UUID friend1Id = UUID.fromString("11111111-1111-1111-1111-111111111112");
            PlatformUser user = new PlatformUser(userId, "TestUser1", "", new ArrayList<>());
            PlatformUser friend = new PlatformUser(new UserId(friend1Id), "TestUser2", "", new ArrayList<>());
            LocalDateTime now = LocalDateTime.now();
            List<PlatformUserFriend> friendRequestList = new ArrayList<>(
                    List.of(
                            new PlatformUserFriend(
                                    UUID.randomUUID(),
                                    user,
                                    friend,
                                    false,
                                    now,
                                    null
                            )
                    )
            );
            Mockito.when(platformUserFriendRepository.findAllFriendRequestsForUser(userId)).thenReturn(friendRequestList);

            //Act
            List<PlatformUserFriend> result = friendService.findFriendRequestsForUser(userId);

            //Assert
            assertThat(result.size()).isEqualTo(1);
            assertThat(result.getFirst().getSender().getUserId()).isEqualTo(userId);
            assertThat(result.getFirst().getReceiver().getUserId().id()).isEqualTo(friend1Id);
            assertThat(result.getFirst().getIsConfirmed()).isEqualTo(false);
            assertThat(result.getFirst().getRequestedAt()).isEqualTo(now);
            assertThat(result.getFirst().getConfirmedAt()).isNull();
            Mockito.verify(platformUserFriendRepository, times(1)).findAllFriendRequestsForUser(userId);
            Mockito.verifyNoMoreInteractions(platformUserFriendRepository);
        }

        @Test
        void getFriendRequestList_should_return_empty_when_no_open_requests(){
            //Arrange
            UserId userId = new UserId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
            Mockito.when(platformUserFriendRepository.findAllFriendRequestsForUser(userId)).thenReturn(new ArrayList<>());

            //Act
            List<PlatformUserFriend> result = friendService.findFriendRequestsForUser(userId);

            //Assert
            assertThat(result.size()).isEqualTo(0);
            Mockito.verify(platformUserFriendRepository, times(1)).findAllFriendRequestsForUser(userId);
            Mockito.verifyNoMoreInteractions(platformUserFriendRepository);

        }
    }

}
