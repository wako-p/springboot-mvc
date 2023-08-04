package jp.wako.demo.springbootmvc.usecase.tasks.get;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class GetTaskResponse {
    private final int id;
    private final String title;
    private final String description;
}
