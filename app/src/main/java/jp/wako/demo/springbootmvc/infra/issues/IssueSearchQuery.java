package jp.wako.demo.springbootmvc.infra.issues;

import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntityDao;
import jp.wako.demo.springbootmvc.infra.issues.sort.Column;
import jp.wako.demo.springbootmvc.infra.issues.sort.Sort;
import jp.wako.demo.springbootmvc.infra.projects.dao.ProjectEntityDao;
import jp.wako.demo.springbootmvc.infra.shared.sort.Order;
import jp.wako.demo.springbootmvc.usecase.issues.search.IIssueSearchQuery;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueDto;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchRequest;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchResponse;
import jp.wako.demo.springbootmvc.usecase.issues.search.ProjectDto;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class IssueSearchQuery implements IIssueSearchQuery {

    private final ProjectEntityDao projectEntityDao;
    private final IssueEntityDao issueEntityDao;

    public IssueSearchResponse execute(final IssueSearchRequest request) {

        var maybeProjectEntity = this.projectEntityDao.selectById(request.getProjectId());
        var foundProjectDto = maybeProjectEntity
            .map(entity -> {
                var dto = new ProjectDto(
                    entity.getId(),
                    entity.getName());
                return dto;
            })
            .orElseThrow(() ->  new UseCaseException(""));

        var condition = new IssueSearchCondition(
            request.getProjectId(),
            request.getTitle(),
            new Sort(
                Column.parseBy(request.getSortColumn()),
                Order.parseBy(request.getSortOrder())));

        var foundIssueEntities = this.issueEntityDao.selectBy(condition);
        var foundIssueDtos = foundIssueEntities
            .stream()
            .map(entity -> {
                var dto = new IssueDto(
                    entity.getId(),
                    entity.getProjectId(),
                    entity.getTitle(),
                    entity.getDescription());
                return dto;
            })
            .collect(Collectors.toList());

        return new IssueSearchResponse(foundProjectDto, foundIssueDtos);
    }

}
