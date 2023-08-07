package jp.wako.demo.springbootmvc.infra.tasks.dao;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.wako.demo.springbootmvc.infra.shared.dao.ImmutableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
@Table(name = "tasks")
public final class TaskEntity extends ImmutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "tasks_id_seq")
    private final Integer id;

    private final String title;
    private final String description;

    public TaskEntity(
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

}
