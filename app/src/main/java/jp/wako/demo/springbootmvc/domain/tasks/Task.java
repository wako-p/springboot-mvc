package jp.wako.demo.springbootmvc.domain.tasks;

import java.time.LocalDateTime;

import jp.wako.demo.springbootmvc.domain.shared.Entity;
import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import lombok.Getter;

@Getter
public final class Task extends Entity {

    // NOTE: IDもEntityに移譲しても良いかも？
    private final Integer id;
    private String title;
    private String description;

    private Task(
        final Integer id,
        final String title,
        final String description,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final int version) {
            super(createdAt, updatedAt, version);
            this.id = id;
            this.title = title;
            this.description = description;
    }

    public static Task create(final String title) {
        if (!isValidTitle(title)) {
            throw new DomainException("");
        }
        return new Task(
            null,
            title,
            "",
            LocalDateTime.now(),
            LocalDateTime.now(),
            1);
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
        final LocalDateTime updateAt,
        final int version) {
        return new Task(id, title, description, createAt, updateAt, version);
    }

    public void update(final String title, final String description) {

        updateTitle(title);
        updateDescription(description);

        this.updatedAt = LocalDateTime.now();
    }

    private void updateTitle(final String title) {
        if (!isValidTitle(title)) {
            throw new DomainException("");
        }
        this.title = title;
    }

    private void updateDescription(final String description) {
        this.description = description;
    }

}
