package be.kdg.ipj3.platformbackend.domain;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }
}
