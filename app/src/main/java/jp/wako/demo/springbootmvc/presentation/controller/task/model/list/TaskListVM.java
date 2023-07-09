package jp.wako.demo.springbootmvc.presentation.controller.task.model.list;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jp.wako.demo.springbootmvc.presentation.controller.task.model.TaskVM;
import lombok.Data;

@Data
public final class TaskListVM {

    @Valid
    private TaskAddFormVM form;
    private List<TaskVM> tasks;

    public TaskListVM() {
        this.form = new TaskAddFormVM();
        this.tasks = new ArrayList<TaskVM>();
    }

}
