package jp.wako.demo.springbootmvc.presentation.controller.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class TaskModel {
    private final String id;
    private final String title;
    private final String comment;
    private final boolean done;
}
