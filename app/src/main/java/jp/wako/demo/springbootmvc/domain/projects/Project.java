package jp.wako.demo.springbootmvc.domain.projects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jp.wako.demo.springbootmvc.domain.shared.Entity;
import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import jp.wako.demo.springbootmvc.domain.tasks.Task;
import lombok.Getter;

@Getter
public final class Project extends Entity {

    private String name;
    private String description;
    List<Task> issues;

    private Project(
        final Integer id,
        final String name,
        final String description,
        final List<Task> tasks,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final Integer version) {
            super(id, createdAt, updatedAt, version);
            this.name = name;
            this.description = description;
            this.issues = tasks;
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

    public static Project reconstruct(
        final Integer id,
        final String name,
        final String description,
        final List<Task> tasks,
        final LocalDateTime createAt,
        final LocalDateTime updateAt,
        final Integer version) {
            return new Project(id, name, description, tasks, createAt, updateAt, version);
    }

    public void add(final Task task) {
        this.issues.add(task);
    }

}
