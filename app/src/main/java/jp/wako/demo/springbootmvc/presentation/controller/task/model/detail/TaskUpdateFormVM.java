package jp.wako.demo.springbootmvc.presentation.controller.task.model.detail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class TaskUpdateFormVM {
    private final String id;
    private final String title;
    private final String description;
    private final boolean done;
}
