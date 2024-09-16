package jp.wako.demo.springbootmvc.domain.projects;

import java.time.LocalDateTime;

import jp.wako.demo.springbootmvc.domain.shared.Entity;
import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import lombok.Getter;

@Getter
public final class Project extends Entity {

    private String name;
    private String description;

    private Project(
        final Long id,
        final String name,
        final String description,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final Long version) {
            super(id, createdAt, updatedAt, version);
            this.name = name;
            this.description = description;
    }

    public static Project create(final String name, final String description) {

        if (!isValidName(name)) {
            throw new DomainException("");
        }

        if (!isValidDescription(description)) {
            throw new DomainException("");
        }

        return new Project(
            null,
            name,
            description,
            LocalDateTime.now().withNano(0),
            LocalDateTime.now().withNano(0),
            1L);
    }

    private static boolean isValidName(final String name) {
        if (name == null || name.isEmpty()) {
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
     * インフラ層でプロジェクトを復元するためのファクトリメソッド
     */
    public static Project reconstruct(
        final Long id,
        final String name,
        final String description,
        final LocalDateTime createAt,
        final LocalDateTime updateAt,
        final Long version) {
            return new Project(id, name, description, createAt, updateAt, version);
    }

}
