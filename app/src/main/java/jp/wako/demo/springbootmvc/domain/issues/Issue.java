package jp.wako.demo.springbootmvc.domain.issues;

import java.time.LocalDateTime;

import jp.wako.demo.springbootmvc.domain.shared.Entity;
import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import lombok.Getter;

/**
 * 課題
 */
@Getter
public final class Issue extends Entity {

    private Long projectId;
    private String title;
    private String description;

    private Issue(
        final Long id,
        final Long projectId,
        final String title,
        final String description,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final Long version) {
            super(id, createdAt, updatedAt, version);
            this.projectId = projectId;
            this.title = title;
            this.description = description;
    }

    public static Issue create(
        final Long projectId,
        final String title,
        final String description) {

        if (!isValidProjectId(projectId)) {
            throw new DomainException("");
        }

        if (!isValidTitle(title)) {
            throw new DomainException("");
        }

        if (!isValidDescription(description)) {
            throw new DomainException("");
        }

        return new Issue(
            null,
            projectId,
            title,
            description,
            LocalDateTime.now().withNano(0),
            LocalDateTime.now().withNano(0),
            1L);
    }

    private static boolean isValidProjectId(final Long projectId) {
        if (projectId == null) {
            return false;
        }
        return true;
    }

    private static boolean isValidTitle(final String title) {
        if (title == null || title.isEmpty()) {
            return false;
        }
        return true;
    }

    private static boolean isValidDescription(final String description) {
        if (description == null) {
            return false;
        }
        return true;
    }

    /**
     * インフラ層で課題を復元するためのファクトリメソッド
     */
    public static Issue reconstruct(
        final Long id,
        final Long projectId,
        final String title,
        final String description,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final Long version) {
        return new Issue(id, projectId, title, description, createdAt, updatedAt, version);
    }

    public void update(final String title, final String description) {

        updateTitle(title);
        updateDescription(description);

        this.updatedAt = LocalDateTime.now().withNano(0);
    }

    private void updateTitle(final String title) {
        if (!isValidTitle(title)) {
            throw new DomainException("");
        }
        this.title = title;
    }

    private void updateDescription(final String description) {
        if (!isValidDescription(description)) {
            throw new DomainException("");
        }
        this.description = description;
    }

}
