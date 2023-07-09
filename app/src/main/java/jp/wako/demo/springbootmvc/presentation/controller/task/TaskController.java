package jp.wako.demo.springbootmvc.presentation.controller.task;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jp.wako.demo.springbootmvc.presentation.controller.task.model.TaskVM;
import jp.wako.demo.springbootmvc.presentation.controller.task.model.detail.TaskDetailVM;
import jp.wako.demo.springbootmvc.presentation.controller.task.model.detail.TaskUpdateFormVM;
import jp.wako.demo.springbootmvc.presentation.controller.task.model.list.TaskCreateFormVM;
import jp.wako.demo.springbootmvc.presentation.controller.task.model.list.TaskListVM;
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
    public String getAll(final Model model) {

        var response = this.getAllTaskUseCase.execute(new GetAllTaskRequest());
        var tasks = response.getTasks()
            .stream()
            .map(task -> new TaskVM(task.getId(), task.getTitle(), task.getDescription(), task.isDone()))
            .collect(Collectors.toList());

        var form = new TaskCreateFormVM("");
        var vm = new TaskListVM(form, tasks);

        model.addAttribute("vm", vm);
        return "/task/task-list";
    }

    @PostMapping("/tasks")
    public String create(final String title) {

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
    public String getBy(final Model model, @PathVariable final String id) {

        var request = new GetTaskRequest(id);
        var response = this.getTaskUseCase.execute(request);

        var form = new TaskUpdateFormVM(response.getId(), response.getTitle(), response.getDescription(), response.isDone());
        var vm = new TaskDetailVM(form);
        model.addAttribute("vm", vm);

        return "/task/task-detail";
    }

    @PutMapping("/tasks/{id}")
    public String update(final Model model, @PathVariable final String id, final String description) {

        var request = new UpdateTaskRequest(id, description);
        var response = this.updateTaskUseCase.execute(request);

        var form = new TaskUpdateFormVM(response.getId(), response.getTitle(), response.getDescription(), response.isDone());
        var vm = new TaskDetailVM(form);
        model.addAttribute("vm", vm);

        return "/task/task-detail";

        // NOTE: forwardでもできるっぽい？
        // return "forward:/tasks/" + id;
    }
}

// presen | infra
// ↓
// usecase
// ↓
// domain