package jp.wako.demo.springbootmvc.domain.shared;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"})
public class Entity {
    private final Integer id;
    private final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    private final Integer version;
}
