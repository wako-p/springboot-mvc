package jp.wako.demo.springbootmvc.presentation.controller.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class TaskForm {
    private final String title;
}
