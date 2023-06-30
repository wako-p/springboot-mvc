package jp.wako.demo.springbootmvc.domain.task;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public final class Task {

    private final String id;
    private final String title;
    private final boolean done;

    public static Task create(final String title) {
        return new Task(UUID.randomUUID().toString(), title, false);
    }

}
