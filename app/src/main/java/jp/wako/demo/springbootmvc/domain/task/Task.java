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
    private String description;
    private final boolean done; // TODO: Enumで状態クラス作成する

    public static Task create(final String title) {
        return new Task(UUID.randomUUID().toString(), title, "No description provided.", false);
    }

    public void updateDescription(final String description) {
        this.description = description;
    }

}
