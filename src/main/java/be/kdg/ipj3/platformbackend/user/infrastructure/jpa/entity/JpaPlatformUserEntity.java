package be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "platform_user")
public class JpaPlatformUserEntity {

    @Id
    @Column
    private UUID id;

    @OneToMany(mappedBy = "user")
    private List<JpaPlatformUserFriendEntity> friends;

    //Todo implement correct fromdomain here for the updates.

    public static JpaPlatformUserEntity fromDomain(PlatformUser user) {
        JpaPlatformUserEntity entity = new JpaPlatformUserEntity();
        entity.id = user.getUserId().id();

        if (user.getFriends() != null && !user.getFriends().isEmpty()) {
            entity.friends = user.getFriends().stream()
                    .map(friend -> {
                        JpaPlatformUserEntity friendEntity = new JpaPlatformUserEntity();
                        friendEntity.id = friend.getUserId().id();
                        return friendEntity;
                    })
                    .toList();
        }

        return entity;
    }

    public PlatformUser toDomain() {
        return PlatformUser.fromDb(this.id, this.friends);
    }

    public PlatformUser toDomainWithoutFriends() {
        return PlatformUser.fromDbWithoutFriends(this.id);
    }


}