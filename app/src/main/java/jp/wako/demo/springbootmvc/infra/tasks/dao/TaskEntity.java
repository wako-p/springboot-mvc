package jp.wako.demo.springbootmvc.infra.tasks.dao;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
import org.seasar.doma.Version;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Data;

@Data
@Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
@Table(name = "tasks")
public final class TaskEntity {
    // TODO: メンバ変数のアクセス修飾子をprivateにする
    // TODO: Entityの基底クラス作成する
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "tasks_id_seq")
    public final Integer id;
    public final String title;
    public final String description;
    public final LocalDateTime createdAt;

    @Version
    public final int version;
}
