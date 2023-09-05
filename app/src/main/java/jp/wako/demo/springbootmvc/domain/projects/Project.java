package jp.wako.demo.springbootmvc.domain.projects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.domain.shared.Entity;
import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import lombok.Getter;

@Getter
public final class Project extends Entity {

    private String name;
    private String description;
    List<Issue> issues;

    private Project(
        final Integer id,
        final String name,
        final String description,
        // TODO: ID連携にする
        final List<Issue> issues,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final Integer version) {
            super(id, createdAt, updatedAt, version);
            this.name = name;
            this.description = description;
            this.issues = issues;
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
            new ArrayList<>(),
            LocalDateTime.now().withNano(0),
            LocalDateTime.now().withNano(0),
            1);
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
        final Integer id,
        final String name,
        final String description,
        final List<Issue> issues,
        final LocalDateTime createAt,
        final LocalDateTime updateAt,
        final Integer version) {
            return new Project(id, name, description, issues, createAt, updateAt, version);
    }

    public void add(final Issue issue) {
        this.issues.add(issue);
    }

}
