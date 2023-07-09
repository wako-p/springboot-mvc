package jp.wako.demo.springbootmvc.presentation.controller.task.model.detail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class TaskDetailVM {
    private final TaskUpdateFormVM form;
}
