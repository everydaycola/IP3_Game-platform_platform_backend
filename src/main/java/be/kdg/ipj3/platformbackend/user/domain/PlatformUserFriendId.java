package be.kdg.ipj3.platformbackend.user.domain;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserEntity;
import be.kdg.ipj3.platformbackend.user.infrastructure.jpa.entity.JpaPlatformUserFriendId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class PlatformUserFriendId {

        private JpaPlatformUserFriendId id;
        private JpaPlatformUserEntity user;


        public PlatformUserFriendId(JpaPlatformUserFriendId id, JpaPlatformUserEntity user) {
            this.id = id;
            this.user = user;
        }
}
