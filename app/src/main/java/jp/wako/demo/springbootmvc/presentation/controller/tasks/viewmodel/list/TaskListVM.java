package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.list;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public final class TaskListVM {

    @Valid
    private TaskCreateFormVM form;
    private List<TaskVM> tasks;

    public TaskListVM() {
        this.form = new TaskCreateFormVM();
        this.tasks = new ArrayList<TaskVM>();
    }

}
