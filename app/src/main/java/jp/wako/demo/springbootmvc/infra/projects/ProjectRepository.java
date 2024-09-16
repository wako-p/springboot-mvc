package jp.wako.demo.springbootmvc.infra.projects;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.seasar.doma.jdbc.UniqueConstraintException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.projects.Project;
import jp.wako.demo.springbootmvc.domain.projects.IProjectRepository;
import jp.wako.demo.springbootmvc.infra.projects.dao.ProjectEntityConverter;
import jp.wako.demo.springbootmvc.infra.projects.dao.ProjectEntityDao;
import jp.wako.demo.springbootmvc.infra.shared.exception.PersistenceException;
import lombok.RequiredArgsConstructor;

@Primary
@RequiredArgsConstructor
@Repository
public class ProjectRepository implements IProjectRepository {

    private final ProjectEntityDao projectEntityDao;
    private final ProjectEntityConverter projectEntityConverter;

    @Override
    public List<Project> findAll() {

        var projects = this.projectEntityDao.selectAll()
            .stream()
            .map(this.projectEntityConverter::toDomain)
            .collect(Collectors.toList());

        return projects;
    }

    @Override
    public Long save(final Project project) {

        var projectEntity = this.projectEntityConverter.toEntity(project);

        try {
            // 新規
            if (projectEntity.getId() == null) {
                var result = this.projectEntityDao.insert(projectEntity);
                var insertedProject = this.projectEntityConverter.toDomain(result.getEntity());
                return insertedProject.getId();
            // 更新
            } else {
                var result = this.projectEntityDao.update(projectEntity);
                var updatedProject = this.projectEntityConverter.toDomain(result.getEntity());
                return updatedProject.getId();
            }
        } catch (OptimisticLockingFailureException ex) {
            throw new PersistenceException("Save failed. It has already been updated. Please try again.", ex);
        } catch (UniqueConstraintException ex) {
            throw new PersistenceException("Save failed. Unique constraint violation.", ex);
        } catch (QueryTimeoutException ex) {
            throw new PersistenceException("Save failed. Query timeout.", ex);
        }
    }

    @Override
    public Optional<Project> findById(final Long id) {
        var maybeProjectEntity = this.projectEntityDao.selectById(id);
        var maybeProject = maybeProjectEntity
            .map(this.projectEntityConverter::toDomain);

        return maybeProject;
    }

}
