package jp.wako.demo.springbootmvc.domain.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public final class Task {

    private final String id;
    private String title;
    private String description;
    private final LocalDateTime createAt;

    public static Task create(final String title) {
        if (!isValidTitle(title)) {
            throw new IllegalArgumentException();
        }
        return new Task(UUID.randomUUID().toString(), title, "", LocalDateTime.now());
    }

    private static boolean isValidTitle(final String title) {
        if (title == null || title.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * インフラ層からタスクを復元するためのファクトリメソッド
     */
    public static Task reconstruct(final String id, final String title, final String description, final LocalDateTime createAt) {
        return new Task(id, title, description, createAt);
    }

    public void updateTitle(final String title) {
        if (!isValidTitle(title)) {
            throw new IllegalArgumentException();
        }
        this.title = title;
    }

    public void updateDescription(final String description) {
        this.description = description;
    }

}
