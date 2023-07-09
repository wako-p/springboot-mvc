package jp.wako.demo.springbootmvc.presentation.controller.task.model.list;

import java.util.List;

import jp.wako.demo.springbootmvc.presentation.controller.task.model.TaskVM;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class TaskListVM {
    private TaskCreateFormVM form;
    private List<TaskVM> tasks;
}
