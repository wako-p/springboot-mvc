package jp.wako.demo.springbootmvc.usecase.task.add;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class AddTaskRequest {
    private final String title;
}
