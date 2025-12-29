package be.kdg.ipj3.platformbackend.lobby.domain;
import be.kdg.ipj3.platformbackend.shared.domain.exception.ConflictException;
import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import be.kdg.ipj3.platformbackend.user.domain.PlatformUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public record Player(PlatformUser user) {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundException notFound() {
        log.error("User {} was not found in lobby", user.getUserId().id());
        return new NotFoundException("User [" + user.getUserId().id() + "] was not found in lobby [" +  "]");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public ConflictException conflict(){
        log.error("User {} is already in lobby",  user.getUserId().id());
        return new ConflictException("User [" +  user.getUserId().id() + "]is already inside lobby [" + "]");
    }

}
