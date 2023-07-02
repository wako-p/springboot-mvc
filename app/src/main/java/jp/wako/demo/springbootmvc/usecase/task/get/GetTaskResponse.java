package jp.wako.demo.springbootmvc.usecase.task.get;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class GetTaskResponse {
    private final String id;
    private final String title;
    private final String comment;
    private final boolean done;
}
