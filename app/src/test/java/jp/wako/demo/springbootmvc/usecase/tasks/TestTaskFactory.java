package jp.wako.demo.springbootmvc.usecase.tasks;

import java.time.LocalDateTime;

import jp.wako.demo.springbootmvc.domain.tasks.Task;

public class TestTaskFactory {

    public static Task create() {
        var task = Task.reconstruct(
            100,
            "title",
            "description",
            LocalDateTime.now().withNano(0),
            LocalDateTime.now().withNano(0),
            1);
        return task;
    }

    public static Task create(final Integer id) {
        var task = Task.reconstruct(
            id,
            "title",
            "description",
            LocalDateTime.now().withNano(0),
            LocalDateTime.now().withNano(0),
            1);
        return task;
    }

    public static Task create(final LocalDateTime createAt, final LocalDateTime updateAt) {
        var task = Task.reconstruct(
            100,
            "title",
            "description",
            createAt,
            updateAt,
            1);
        return task;
    }

    public static Task create(final Integer id, final LocalDateTime createAt, final LocalDateTime updateAt) {
        var task = Task.reconstruct(
            id,
            "title",
            "description",
            createAt,
            updateAt,
            1);
        return task;
    }

}
