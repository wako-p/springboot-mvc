package jp.wako.demo.springbootmvc.presentation.controller.task.model.list;

import java.util.ArrayList;
import java.util.List;

import jp.wako.demo.springbootmvc.presentation.controller.task.model.TaskVM;
import lombok.Data;

@Data
public final class TaskListVM {

    private TaskCreateFormVM form;
    private List<TaskVM> tasks;

    public TaskListVM() {
        this.form = new TaskCreateFormVM();
        this.tasks = new ArrayList<TaskVM>();
    }

}
