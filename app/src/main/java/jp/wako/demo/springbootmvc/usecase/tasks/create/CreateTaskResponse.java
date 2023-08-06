package jp.wako.demo.springbootmvc.usecase.tasks.create;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class CreateTaskResponse {
    private final int id;
}
