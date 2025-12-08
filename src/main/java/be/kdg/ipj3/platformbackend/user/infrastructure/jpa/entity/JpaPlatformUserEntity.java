package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUserFriend;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "platform_user")
@Access(AccessType.FIELD)
public class JpaPlatformUserEntity {

    @Id
    @Column
    private UUID id;

    @Column
    private String userName;

    @Column
    private String biography;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaFriendRequestEntity> sentFriendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaFriendRequestEntity> receivedFriendRequests = new ArrayList<>();


    protected JpaPlatformUserEntity() {
    }

    private JpaPlatformUserEntity(UUID id,String userName, String biography, List<JpaFriendRequestEntity> friends) {
        this.id = id;
        this.userName = userName;
        this.biography = biography;
        this.receivedFriendRequests = friends;
    }

    public static JpaPlatformUserEntity fromDomain(PlatformUser domain) {
        JpaPlatformUserEntity entity = new JpaPlatformUserEntity();
        entity.id = domain.getUserId().id();
        entity.userName = domain.getUserName();
        entity.biography = domain.getBiography();

        entity.receivedFriendRequests = domain.getFriends().stream()
                .map(friend -> JpaFriendRequestEntity.fromDomain(
                        friend,
                        entity,
                        new JpaPlatformUserEntity(friend.getReceiver().getUserId().id(), "","", List.of())
                ))
                .toList();

        return entity;
    }

    public PlatformUser toDomain() {
        List<PlatformUserFriend> friendList = receivedFriendRequests.stream()
                .map(JpaFriendRequestEntity::toDomain)
                .collect(Collectors.toList());

        return PlatformUser.fromDb(id, userName,biography,friendList);
    }

    public PlatformUser toDomainWithoutFriends() {
        return PlatformUser.fromDb(id, userName,biography,new ArrayList<>());
    }

}