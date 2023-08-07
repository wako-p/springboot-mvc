package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public final class TaskIndexVM {

    @Valid
    private TaskCreateVM taskCreateVM;
    private List<TaskVM> tasks;

    public TaskIndexVM() {
        this.taskCreateVM = new TaskCreateVM();
        this.tasks = new ArrayList<TaskVM>();
    }

}
