package jp.wako.demo.springbootmvc.usecase.task.delete;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class DeleteTaskRequest {
    private final String id;
}
