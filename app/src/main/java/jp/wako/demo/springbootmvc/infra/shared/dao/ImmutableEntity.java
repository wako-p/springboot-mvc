package jp.wako.demo.springbootmvc.infra.shared.dao;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.Version;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Data;

@Data
@Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
public abstract class ImmutableEntity {

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Version
    private final Long version;
}
