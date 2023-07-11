package jp.wako.demo.springbootmvc.presentation.controller.task;

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

import jp.wako.demo.springbootmvc.presentation.controller.task.model.TaskVM;
import jp.wako.demo.springbootmvc.presentation.controller.task.model.detail.TaskDetailVM;
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
import lombok.extern.log4j.Log4j2;

@Log4j2
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
    public String getAll(@ModelAttribute("taskListVM") final TaskListVM vm) {

        var response = this.getAllTaskUseCase.execute(new GetAllTaskRequest());
        var tasks = response.getTasks()
            .stream()
            .map(task -> new TaskVM(task.getId(), task.getTitle(), task.getDescription(), task.isDone()))
            .collect(Collectors.toList());

        vm.setTasks(tasks);

        return "/task/task-list";
    }

    @PostMapping("/tasks")
    public String create(@ModelAttribute("taskListVM") @Validated final TaskListVM vm, final BindingResult result) {

        var form = vm.getForm();

        if (result.hasErrors()) {
            log.info("validation error");
            // NOTE: フォワード先のURLがPostMappingと同じなのでStackOverflowになる
            // return "forward:/tasks";
            return "redirect:/tasks";
        }
        log.info("not validation error");

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

    @GetMapping("/tasks/{id}")
    public String get(@PathVariable final String id, @ModelAttribute("taskDetailVM") final TaskDetailVM vm) {

        var request = new GetTaskRequest(id);
        var response = this.getTaskUseCase.execute(request);

        var form = vm.getForm();
        form.setId(response.getId());
        form.setTitle(response.getTitle());
        form.setDescription(response.getDescription());
        form.setDone(response.isDone());

        return "/task/task-detail";
    }

    @PutMapping("/tasks/{id}")
    public String update(@PathVariable final String id, @ModelAttribute("taskDetailVM") final TaskDetailVM vm) {

        var form = vm.getForm();
        var request = new UpdateTaskRequest(id, form.getTitle(), form.getDescription());
        var response = this.updateTaskUseCase.execute(request);

        return "redirect:/tasks/" + response.getId();
    }
}
