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

import jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.edit.TaskEditVM;
import jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.list.TaskListVM;
import jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.list.TaskVM;
import jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.view.TaskViewVM;
import jp.wako.demo.springbootmvc.usecase.tasks.create.CreateTaskRequest;
import jp.wako.demo.springbootmvc.usecase.tasks.create.CreateTaskUseCase;
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
    private final GetTaskUseCase getTaskUseCase;
    private final CreateTaskUseCase createTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

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
        var request = new CreateTaskRequest(form.getTitle());
        this.createTaskUseCase.execute(request);

        return "redirect:/tasks";
    }

    @ModelAttribute("taskViewVM")
    private TaskViewVM initializeTaskViewVM() {
        return new TaskViewVM();
    }

    @GetMapping("/tasks/{id}/view")
    public String view(
        @PathVariable final int id,
        @ModelAttribute("taskViewVM") final TaskViewVM vm) {

        var request = new GetTaskRequest(id);
        var response = this.getTaskUseCase.execute(request);

        vm.setId(response.getId());
        vm.setTitle(response.getTitle());
        vm.setDescription(response.getDescription());

        return "/tasks/task-view";
    }

    @ModelAttribute("taskEditVM")
    private TaskEditVM initializeTaskEditVM() {
        return new TaskEditVM();
    }

    @GetMapping("/tasks/{id}/edit")
    public String edit(
        @PathVariable final int id,
        @ModelAttribute("taskEditVM") final TaskEditVM vm) {

        var request = new GetTaskRequest(id);
        var response = this.getTaskUseCase.execute(request);

        vm.setId(response.getId());
        vm.setTitle(response.getTitle());
        vm.setDescription(response.getDescription());
        vm.setVersion(response.getVersion());

        return "/tasks/task-edit";
    }

    @PutMapping("/tasks/{id}")
    public String update(
        @PathVariable final int id,
        @ModelAttribute("taskEditVM") @Validated final TaskEditVM vm,
        final BindingResult result) {

        if (result.hasErrors()) {
            return "/tasks/task-edit";
        }

        // TODO: PersistenceException キャッチしたら /tasks/{id}/edit にリダイレクトさせる
        var request = new UpdateTaskRequest(id, vm.getTitle(), vm.getDescription(), vm.getVersion());
        var response = this.updateTaskUseCase.execute(request);

        return "redirect:/tasks/" + response.getId() + "/view";
    }

    @DeleteMapping("/tasks/{id}")
    public String delete(@PathVariable final int id) {

        var request = new DeleteTaskRequest(id);
        this.deleteTaskUseCase.execute(request);

        return "redirect:/tasks";
    }

}
