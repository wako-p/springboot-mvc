package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TaskVM {
    private final String id;
    private final String title;
    private final String description;
    private final String createAt;
}
