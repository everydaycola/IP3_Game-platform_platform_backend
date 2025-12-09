package be.kdg.ipj3.platformbackend.user.domain;

import be.kdg.ipj3.platformbackend.shared.domain.UserId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Slf4j
public class PlatformUser {
    private final UserId userId;

    private final String userName;
    private final String biography;
    private List<PlatformFriendRequest> sentFriendRequests = new ArrayList<>();
    private List<PlatformFriendRequest> receivedFriendRequests = new ArrayList<>();

    public PlatformUser(UserId userId, String userName, String biography, List<PlatformFriendRequest> sentFriendRequests, List<PlatformFriendRequest> receivedFriendRequests) {
        this.userId = userId;
        this.userName = userName;
        this.biography = biography;
        this.sentFriendRequests = sentFriendRequests != null ? sentFriendRequests : new ArrayList<>();
        this.receivedFriendRequests = receivedFriendRequests != null ? receivedFriendRequests : new ArrayList<>();
    }

    public List<UserId> getFriendIds() {
        List<UserId> friendIds = new ArrayList<>();

        friendIds.addAll(this.sentFriendRequests.stream()
                .map(friend -> {
                    UUID friendId = friend.getReceiver().getUserId().id();
                    return new UserId(friendId);
                })
                .toList()
        );

        friendIds.addAll(this.receivedFriendRequests.stream()
                .map(friend -> {
                    UUID friendId = friend.getSender().getUserId().id();
                    return new UserId(friendId);
                })
                .toList()
        );
        return friendIds;
    }

}
