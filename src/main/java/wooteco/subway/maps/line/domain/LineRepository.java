package wooteco.subway.maps.line.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wooteco.subway.maps.line.domain.line.Line;

public interface LineRepository extends JpaRepository<Line, Long> {
    @Override
    List<Line> findAll();
}
