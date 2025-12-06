package be.kdg.ipj3.platformbackend.user;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import be.kdg.ipj3.platformbackend.user.application.FriendService;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserFriendRepository;
import be.kdg.ipj3.platformbackend.user.domain.repository.PlatformUserRepository;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriendId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FriendRequestTest {

    @Mock
    PlatformUserRepository platformUserRepository;

    @Mock
    PlatformUserFriendRepository platformUserFriendRepository;

    @InjectMocks
    FriendService friendService;

    @Nested
    class SuccesFlows {

        @Test
        void addFriendToFriendList_should_addNewFriend_whenNoExistingRequest() {
            //Arrange
            UserId userId = new UserId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
            UserId friend1Id = new UserId(UUID.fromString("11111111-1111-1111-1111-111111111112"));

            PlatformUser user = new PlatformUser(userId, new ArrayList<>());
            PlatformUser friend = new PlatformUser(friend1Id, new ArrayList<>());
            Mockito.when(platformUserRepository.findUserById(userId)).thenReturn(user);
            Mockito.when(platformUserRepository.findUserById(friend1Id)).thenReturn(friend);
            Mockito.when(platformUserFriendRepository.findFriendRequestBetween(friend1Id.id(), userId))
                    .thenThrow(new NotFoundException("No request found"));

            //Act
            friendService.addFriendRequest(userId, friend1Id);
            //Assert
            Mockito.verify(platformUserRepository, times(1)).findUserById(userId);
            Mockito.verify(platformUserFriendRepository, times(1)).findFriendRequestBetween(friend1Id.id(), userId);
            Mockito.verify(platformUserRepository, times(1)).save(user);

        }

        @Test
        void addFriendToFriendList_should_acceptExistingRequest_whenRequestExists() {
            //Arrange
            UserId userId = new UserId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
            UserId friend1Id = new UserId(UUID.fromString("11111111-1111-1111-1111-111111111112"));

            PlatformUser user = new PlatformUser(userId, new ArrayList<>());
            PlatformUser friend = new PlatformUser(friend1Id, new ArrayList<>());
            PlatformUserFriend existingRequest = new PlatformUserFriend(new PlatformUserFriendId(userId.id(), friend1Id.id()),false,LocalDateTime.now(),null);
            Mockito.when(platformUserRepository.findUserById(userId)).thenReturn(user);
            Mockito.when(platformUserRepository.findUserById(friend1Id)).thenReturn(friend);
            Mockito.when(platformUserFriendRepository.findFriendRequestBetween(friend1Id.id(), userId))
                    .thenReturn(existingRequest);

            //Act
            friendService.addFriendRequest(userId, friend1Id);
            //Assert

            Mockito.verify(platformUserRepository, times(1)).findUserById(userId);
            //The second call occurs in the 'acceptFriendRequest method' which is invoked within.
            Mockito.verify(platformUserFriendRepository, times(2)).findFriendRequestBetween(friend1Id.id(), userId);

        }

        @Test
        void removeFriendFromFriendlist_should_removeplatform_user_friend() {
            //Arrange
            UserId userId = new UserId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
            UserId friend1Id = new UserId(UUID.fromString("11111111-1111-1111-1111-111111111112"));
            //Act
            friendService.removeFriendFromFriendList(userId,friend1Id);
            //Assert
            Mockito.verify(platformUserFriendRepository, times(1)).remove(userId, friend1Id);
        }

        @Test
        void acceptFriendRequest_should_update_the_state_of_an_existing_friend_request() {
            //Arrange
            UserId userId = new UserId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
            UUID friend1Id = UUID.fromString("11111111-1111-1111-1111-111111111112");
            LocalDateTime now = LocalDateTime.now();
            PlatformUserFriend testFriend = new PlatformUserFriend(new PlatformUserFriendId(userId.id(), friend1Id),false, now,null);
            Mockito.when(platformUserFriendRepository.findFriendRequestBetween(friend1Id,userId)).thenReturn(testFriend);

            //Act
            friendService.acceptFriendRequest(userId, friend1Id);

            //Assert
            Mockito.verify(platformUserFriendRepository, times(1)).findFriendRequestBetween(friend1Id,userId);
        }

    }

}
