package jp.wako.demo.springbootmvc.usecase.task.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class UpdateTaskResponse {
    private final String id;
    private final String title;
    private final String comment;
    private final boolean done;
}
