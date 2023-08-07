package jp.wako.demo.springbootmvc.usecase.tasks.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class UpdateTaskRequest {
    private final int id;
    private final String title;
    private final String description;
    private final int version;
}
