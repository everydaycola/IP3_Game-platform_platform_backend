package be.kdg.ipj3.platformbackend.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public class OwnedCopy {
    private OwnedCopyId id;
    @Setter
    private boolean isFavorite;
}
