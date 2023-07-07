package jp.wako.demo.springbootmvc.usecase.task.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class UpdateTaskRequest {
    private final String id;
    private final String description;
}
