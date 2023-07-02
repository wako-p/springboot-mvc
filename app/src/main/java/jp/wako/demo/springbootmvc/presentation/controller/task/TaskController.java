package jp.wako.demo.springbootmvc.presentation.controller.task;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.wako.demo.springbootmvc.usecase.task.add.AddTaskRequest;
import jp.wako.demo.springbootmvc.usecase.task.add.AddTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.task.delete.DeleteTaskRequest;
import jp.wako.demo.springbootmvc.usecase.task.delete.DeleteTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.task.getall.GetAllTaskRequest;
import jp.wako.demo.springbootmvc.usecase.task.getall.GetAllTaskUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TaskController {

    private final GetAllTaskUseCase getAllTaskUseCase;
    private final AddTaskUseCase addTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    @GetMapping("/tasks")
    public String get(final Model model) {

        var response = this.getAllTaskUseCase.execute(new GetAllTaskRequest());
        var tasks = response.getTasks()
            .stream()
            .map(task -> new TaskModel(task.getId(), task.getTitle(), task.isDone()))
            .collect(Collectors.toList());

        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @PostMapping("/tasks")
    public String post(final String title) {

        var request = new AddTaskRequest(title);
        this.addTaskUseCase.execute(request);

        return "redirect:/tasks";
    }

    @DeleteMapping("/tasks/{id}")
    public String delete(@PathVariable final String id) {

        var request = new DeleteTaskRequest(id);
        this.deleteTaskUseCase.execute(request);

        return "redirect:/tasks";
    }

}

// presen | infra
// ↓
// usecase
// ↓
// domain