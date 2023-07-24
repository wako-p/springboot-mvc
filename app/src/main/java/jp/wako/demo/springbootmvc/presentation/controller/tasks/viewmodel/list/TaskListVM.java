package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.list;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
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
