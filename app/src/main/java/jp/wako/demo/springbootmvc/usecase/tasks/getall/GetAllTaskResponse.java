package jp.wako.demo.springbootmvc.usecase.tasks.getall;

import java.util.List;

import jp.wako.demo.springbootmvc.domain.tasks.Task;
import lombok.Data;

@Data
public final class GetAllTaskResponse {
    private final List<Task> tasks;
}
