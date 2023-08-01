package jp.wako.demo.springbootmvc.infra.tasks.dao;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
@Table(name = "tasks")
public final class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "tasks_id_seq")
    public Integer id;
    public String title;
    public String description;
    public LocalDateTime createdAt;
}
