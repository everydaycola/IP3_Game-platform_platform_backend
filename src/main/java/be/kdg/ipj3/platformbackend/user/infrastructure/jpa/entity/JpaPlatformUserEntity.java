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
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaPlatformUserFriendEntity> friends = new ArrayList<>();

    protected JpaPlatformUserEntity() {
    }

    private JpaPlatformUserEntity(UUID id, List<JpaPlatformUserFriendEntity> friends) {
        this.id = id;
        this.friends = friends;
    }

    public static JpaPlatformUserEntity fromDomain(PlatformUser domain) {
        JpaPlatformUserEntity entity = new JpaPlatformUserEntity();
        entity.id = domain.getUserId().id();

        entity.friends = domain.getFriends().stream()
                .map(friend -> JpaPlatformUserFriendEntity.fromDomain(
                        friend,
                        entity,
                        new JpaPlatformUserEntity(friend.getId().getFriendId(), List.of())
                ))
                .toList();

        return entity;
    }

    public PlatformUser toDomain() {
        List<PlatformUserFriend> friendList = friends.stream()
                .map(JpaPlatformUserFriendEntity::toDomain)
                .collect(Collectors.toList());

        return PlatformUser.fromDb(id, friendList);
    }

}