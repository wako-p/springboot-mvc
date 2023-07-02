package jp.wako.demo.springbootmvc.presentation.controller.task;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.wako.demo.springbootmvc.usecase.task.add.AddTaskRequest;
import jp.wako.demo.springbootmvc.usecase.task.add.AddTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.task.getall.GetAllTaskRequest;
import jp.wako.demo.springbootmvc.usecase.task.getall.GetAllTaskUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TaskController {

    private final GetAllTaskUseCase getAllTaskUseCase;
    private final AddTaskUseCase addTaskUseCase;

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

    @PostMapping("/task")
    public String post(final Model model, final String title) {

        var request = new AddTaskRequest(title);
        this.addTaskUseCase.execute(request);

        return "redirect:tasks";
    }

    // @GetMapping("/task/{id}")
    // public String get(@PathVariable final String id, final Model model) {
    //     val taskModel = new TaskModel(id, "test", false);
    //     model.addAttribute("task", taskModel);
    //     return "redirect:tasks";
    // }

}

// presen | infra
// ↓
// usecase
// ↓
// domain