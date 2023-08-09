package jp.wako.demo.springbootmvc.domain.shared;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Entity {
    private final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    private final int version;
}
