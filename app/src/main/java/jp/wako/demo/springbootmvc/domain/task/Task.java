package jp.wako.demo.springbootmvc.domain.task;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public final class Task {

    private final String id;
    private final String title;
    private String comment;
    private final boolean done;

    public static Task create(final String title) {
        return new Task(UUID.randomUUID().toString(), title, "No description provided.", false);
    }

    public void updateComment(final String comment) {
        this.comment = comment;
    }
}
