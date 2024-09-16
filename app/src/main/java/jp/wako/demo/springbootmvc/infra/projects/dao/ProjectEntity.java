package jp.wako.demo.springbootmvc.infra.projects.dao;

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
@Table(name = "projects")
public final class ProjectEntity extends ImmutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "projects_id_seq")
    private final Long id;

    private final String name;
    private final String description;

    public ProjectEntity(
        final Long id,
        final String name,
        final String description,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final Long version) {
            super(createdAt, updatedAt, version);
            this.id = id;
            this.name = name;
            this.description = description;
    }

}
