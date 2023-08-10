package jp.wako.demo.springbootmvc.usecase.tasks.create;

import lombok.Data;

@Data
public final class CreateTaskRequest {
    private final String title;
}
