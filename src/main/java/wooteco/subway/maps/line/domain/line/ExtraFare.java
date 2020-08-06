package wooteco.subway.maps.line.domain.line;

import javax.persistence.Embeddable;

import wooteco.subway.maps.line.domain.line.exception.InvalidLineCreationException;

@Embeddable
public class ExtraFare {
    private int value;

    protected ExtraFare() {}

    public ExtraFare(int extraFare) {
        if (extraFare < 0) {
            throw new InvalidLineCreationException("추가 요금은 음수일 수 없습니다.");
        }
        this.value = extraFare;
    }

    public int getExtraFare() {
        return value;
    }
}
