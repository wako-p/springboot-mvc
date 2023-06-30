package jp.wako.demo.springbootmvc.usecase.task.getall;

import java.util.List;

import jp.wako.demo.springbootmvc.domain.task.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class GetAllTaskResponse {
    private final List<Task> tasks;
}
