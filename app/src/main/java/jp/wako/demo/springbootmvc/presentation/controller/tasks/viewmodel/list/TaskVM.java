package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.list;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TaskVM {
    private final int id;
    private final String title;
    private final String description;
    private final String createAt;
}
