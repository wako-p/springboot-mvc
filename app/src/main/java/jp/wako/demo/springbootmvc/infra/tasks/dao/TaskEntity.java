package jp.wako.demo.springbootmvc.infra.tasks.dao;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public final class TaskEntity {
    private final String id;
    private final String title;
    private final String description;
    private final LocalDateTime createAt;
}
