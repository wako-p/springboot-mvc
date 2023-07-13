package jp.wako.demo.springbootmvc.domain.tasks;

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
    private final boolean done; // TODO: Enumで状態クラス作成する

    public static Task create(final String title) {
        return new Task(UUID.randomUUID().toString(), title, "No description provided.", false);
    }

    /**
     * インフラ層からタスクを復元するためのファクトリメソッド
     * @param id
     * @param title
     * @param description
     * @param done
     * @return
     */
    public static Task reconstruct(final String id, final String title, final String description, final boolean done) {
        return new Task(id, title, description, done);
    }

    public void updateTitle(final String title) {
        this.title = title;
    }

    public void updateDescription(final String description) {
        this.description = description;
    }

}
