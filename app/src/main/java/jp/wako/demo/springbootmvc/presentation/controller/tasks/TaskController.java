package jp.wako.demo.springbootmvc.presentation.controller.tasks;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.TaskVM;
import jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.detail.TaskDetailVM;
import jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.list.TaskListVM;
import jp.wako.demo.springbootmvc.usecase.tasks.add.AddTaskRequest;
import jp.wako.demo.springbootmvc.usecase.tasks.add.AddTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.tasks.delete.DeleteTaskRequest;
import jp.wako.demo.springbootmvc.usecase.tasks.delete.DeleteTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.tasks.get.GetTaskRequest;
import jp.wako.demo.springbootmvc.usecase.tasks.get.GetTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.tasks.getall.GetAllTaskRequest;
import jp.wako.demo.springbootmvc.usecase.tasks.getall.GetAllTaskUseCase;
import jp.wako.demo.springbootmvc.usecase.tasks.update.UpdateTaskRequest;
import jp.wako.demo.springbootmvc.usecase.tasks.update.UpdateTaskUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TaskController {

    private final GetAllTaskUseCase getAllTaskUseCase;
    private final AddTaskUseCase addTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetTaskUseCase getTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;

    @ModelAttribute("taskListVM")
    private TaskListVM initializeTaskListVM() {
        return new TaskListVM();
    }

    @GetMapping("/tasks")
    public String getAll(
        @ModelAttribute("taskListVM") final TaskListVM vm) {

        var response = this.getAllTaskUseCase.execute(new GetAllTaskRequest());
        var tasks = response.getTasks()
            .stream()
            .map(task -> new TaskVM(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isDone(),
                task.getCreateAt().toString()))
            .collect(Collectors.toList());

        vm.setTasks(tasks);

        return "/tasks/task-list";
    }

    @PostMapping("/tasks")
    public String create(
        @ModelAttribute("taskListVM") @Validated final TaskListVM vm,
        final BindingResult result) {

        if (result.hasErrors()) {
            return getAll(vm);
        }

        var form = vm.getForm();
        var request = new AddTaskRequest(form.getTitle());
        this.addTaskUseCase.execute(request);

        return "redirect:/tasks";
    }

    @DeleteMapping("/tasks/{id}")
    public String delete(@PathVariable final String id) {

        var request = new DeleteTaskRequest(id);
        this.deleteTaskUseCase.execute(request);

        return "redirect:/tasks";
    }

    @ModelAttribute("taskDetailVM")
    private TaskDetailVM initializeTaskDetailVM() {
        return new TaskDetailVM();
    }

    @GetMapping("/tasks/{id}/view")
    public String view(
        @PathVariable final String id,
        @ModelAttribute("taskDetailVM") final TaskDetailVM vm) {

        var request = new GetTaskRequest(id);
        var response = this.getTaskUseCase.execute(request);

        var form = vm.getForm();
        form.setId(response.getId());
        form.setTitle(response.getTitle());
        form.setDescription(response.getDescription());
        form.setDone(response.isDone());

        return "/tasks/task-view";
    }

    @GetMapping("/tasks/{id}/edit")
    public String edit(
        @PathVariable final String id,
        @ModelAttribute("taskDetailVM") final TaskDetailVM vm) {

        var request = new GetTaskRequest(id);
        var response = this.getTaskUseCase.execute(request);

        var form = vm.getForm();
        form.setId(response.getId());
        form.setTitle(response.getTitle());
        form.setDescription(response.getDescription());
        form.setDone(response.isDone());

        return "/tasks/task-edit";
    }

    @PutMapping("/tasks/{id}")
    public String update(
        @PathVariable final String id,
        @ModelAttribute("taskDetailVM") @Validated final TaskDetailVM vm,
        final BindingResult result) {

        if (result.hasErrors()) {
            return "/tasks/task-edit";
        }

        var form = vm.getForm();
        var request = new UpdateTaskRequest(id, form.getTitle(), form.getDescription());
        var response = this.updateTaskUseCase.execute(request);

        return "redirect:/tasks/" + response.getId() + "/view";
    }
}
