package jp.wako.demo.springbootmvc.infra.projects.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
import org.seasar.doma.Transient;
import org.seasar.doma.jdbc.entity.NamingType;

import jp.wako.demo.springbootmvc.infra.shared.dao.ImmutableEntity;
import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntity;
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
    private final Integer id;

    private final String name;
    private final String description;

    @Transient
    private List<IssueEntity> issues;

    public ProjectEntity(
        final Integer id,
        final String name,
        final String description,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final Integer version) {
            super(createdAt, updatedAt, version);
            this.id = id;
            this.name = name;
            this.description = description;
            this.issues = new ArrayList<>();
    }

}
