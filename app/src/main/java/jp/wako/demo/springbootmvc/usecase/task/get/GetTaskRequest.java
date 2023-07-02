package jp.wako.demo.springbootmvc.usecase.task.get;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class GetTaskRequest {
    private final String id;
}
