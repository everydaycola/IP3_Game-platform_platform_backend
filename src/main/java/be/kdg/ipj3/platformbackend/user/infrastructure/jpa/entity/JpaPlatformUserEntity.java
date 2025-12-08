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

    @Column
    private String userName;

    @Column
    private String biography;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaPlatformUserFriendEntity> friends = new ArrayList<>();

    protected JpaPlatformUserEntity() {
    }

    private JpaPlatformUserEntity(UUID id,String userName, String biography, List<JpaPlatformUserFriendEntity> friends) {
        this.id = id;
        this.userName = userName;
        this.biography = biography;
        this.friends = friends;
    }

    public static JpaPlatformUserEntity fromDomain(PlatformUser domain) {
        JpaPlatformUserEntity entity = new JpaPlatformUserEntity();
        entity.id = domain.getUserId().id();
        entity.userName = domain.getUserName();
        entity.biography = domain.getBiography();

        entity.friends = domain.getFriends().stream()
                .map(friend -> JpaPlatformUserFriendEntity.fromDomain(
                        friend,
                        entity,
                        //Strings here are enough since the ID relation between friends is enough.
                        new JpaPlatformUserEntity(friend.getId().getFriendId(),"","", List.of())
                ))
                .toList();

        return entity;
    }

    public PlatformUser toDomain() {
        List<PlatformUserFriend> friendList = friends.stream()
                .map(JpaPlatformUserFriendEntity::toDomain)
                .collect(Collectors.toList());

        return PlatformUser.fromDb(id, userName,biography,friendList, new ArrayList<>());
    }

}