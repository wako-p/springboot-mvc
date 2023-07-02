package jp.wako.demo.springbootmvc.presentation.controller.task;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jp.wako.demo.springbootmvc.usecase.task.add.AddTaskRequest;
import jp.wako.demo.springbootmvc.usecase.task.add.AddTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.task.delete.DeleteTaskRequest;
import jp.wako.demo.springbootmvc.usecase.task.delete.DeleteTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.task.get.GetTaskRequest;
import jp.wako.demo.springbootmvc.usecase.task.get.GetTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.task.getall.GetAllTaskRequest;
import jp.wako.demo.springbootmvc.usecase.task.getall.GetAllTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.task.update.UpdateTaskRequest;
import jp.wako.demo.springbootmvc.usecase.task.update.UpdateTaskUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TaskController {

    private final GetAllTaskUseCase getAllTaskUseCase;
    private final AddTaskUseCase addTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetTaskUseCase getTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;

    @GetMapping("/tasks")
    public String get(final Model model) {

        var response = this.getAllTaskUseCase.execute(new GetAllTaskRequest());
        var tasks = response.getTasks()
            .stream()
            .map(task -> new TaskModel(task.getId(), task.getTitle(), task.getComment(), task.isDone()))
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

    @GetMapping("/tasks/{id}")
    public String get(final Model model, @PathVariable final String id) {

        var request = new GetTaskRequest(id);
        var response = this.getTaskUseCase.execute(request);

        var task = new TaskModel(response.getId(), response.getTitle(), response.getComment(), response.isDone());
        model.addAttribute("task", task);

        return "/task";
    }

    @PutMapping("/tasks/{id}")
    public String put(final Model model, @PathVariable final String id, final String comment) {

        var request = new UpdateTaskRequest(id, comment);
        var response = this.updateTaskUseCase.execute(request);

        // TODO: redirectのがよさげ？
        var task = new TaskModel(response.getId(), response.getTitle(), response.getComment(), response.isDone());
        model.addAttribute("task", task);

        return "/task";
    }
}

// presen | infra
// ↓
// usecase
// ↓
// domain