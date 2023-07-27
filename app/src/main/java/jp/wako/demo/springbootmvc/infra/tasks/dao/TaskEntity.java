package jp.wako.demo.springbootmvc.infra.tasks.dao;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Entity(immutable = true)
@Table(name = "tasks")
public final class TaskEntity {
    public int id;
    public String title;
    public String description;
    public LocalDateTime createAt;
}
