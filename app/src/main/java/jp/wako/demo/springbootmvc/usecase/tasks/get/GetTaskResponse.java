package jp.wako.demo.springbootmvc.usecase.tasks.get;

import lombok.Data;

@Data
public final class GetTaskResponse {
    private final int id;
    private final String title;
    private final String description;
    private final int version;
}
