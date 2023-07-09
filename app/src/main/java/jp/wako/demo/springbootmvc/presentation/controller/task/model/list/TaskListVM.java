package jp.wako.demo.springbootmvc.presentation.controller.task.model.list;

import java.util.List;

import jp.wako.demo.springbootmvc.presentation.controller.task.model.TaskVM;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class TaskListVM {
    private final TaskCreateFormVM form;
    private final List<TaskVM> tasks;
}
