package jp.wako.demo.springbootmvc.infra.issues.dao;

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
@Table(name = "issues")
public final class IssueEntity extends ImmutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "issues_id_seq")
    private final Long id;
    private final Long projectId;
    private final String title;
    private final String description;

    public IssueEntity(
        final Long id,
        final Long projectId,
        final String title,
        final String description,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final Long version) {
            super(createdAt, updatedAt, version);
            this.id = id;
            this.projectId = projectId;
            this.title = title;
            this.description = description;
    }

}
