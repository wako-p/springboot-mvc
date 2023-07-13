package jp.wako.demo.springbootmvc.usecase.tasks.get;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class GetTaskRequest {
    private final String id;
}
