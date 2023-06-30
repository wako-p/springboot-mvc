package jp.wako.demo.springbootmvc.usecase.task.add;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class AddTaskResponse {
    private final String id;
    private final String title;
    private final boolean done;
}
