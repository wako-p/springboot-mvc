package jp.wako.demo.springbootmvc.domain.tasks;

import java.time.LocalDateTime;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public final class Task {

    private final Integer id;
    private String title;
    private String description;
    private final LocalDateTime createdAt;
    private final int version;

    public static Task create(final String title) {
        if (!isValidTitle(title)) {
            throw new DomainException("");
        }
        return new Task(null, title, "", LocalDateTime.now(), 1);
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
    public static Task reconstruct(
        final Integer id,
        final String title,
        final String description,
        final LocalDateTime createAt,
        final int version) {
        return new Task(id, title, description, createAt, version);
    }

    public void updateTitle(final String title) {
        if (!isValidTitle(title)) {
            throw new DomainException("");
        }
        this.title = title;
    }

    public void updateDescription(final String description) {
        this.description = description;
    }

}
