package jp.wako.demo.springbootmvc.presentation.controller.task.model.list;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class TaskCreateFormVM {
    private final String title;
}
