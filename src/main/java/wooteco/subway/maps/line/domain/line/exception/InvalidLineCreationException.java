package wooteco.subway.maps.line.domain.line.exception;

public class InvalidLineCreationException extends IllegalArgumentException{
    public InvalidLineCreationException(String message) {
        super(message);
    }
}
