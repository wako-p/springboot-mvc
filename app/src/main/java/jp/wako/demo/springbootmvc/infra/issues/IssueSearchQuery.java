package jp.wako.demo.springbootmvc.infra.issues;

import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntityDao;
import jp.wako.demo.springbootmvc.infra.issues.sort.Column;
import jp.wako.demo.springbootmvc.infra.issues.sort.Sort;
import jp.wako.demo.springbootmvc.infra.shared.sort.Order;
import jp.wako.demo.springbootmvc.usecase.issues.search.IIssueSearchQuery;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueDto;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchRequest;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class IssueSearchQuery implements IIssueSearchQuery {

    private final IssueEntityDao dao;

    public IssueSearchResponse execute(final IssueSearchRequest request) {

        var condition = new IssueSearchCondition(
            request.getProjectId(),
            request.getTitle(),
            new Sort(
                Column.parseBy(request.getSortColumn()),
                Order.parseBy(request.getSortOrder())));

        var foundIssueEntities = this.dao.selectBy(condition);
        var foundIssueDtos = foundIssueEntities
            .stream()
            .map(foundIssueEntity -> {
                var foundIssueDto = new IssueDto(
                    foundIssueEntity.getId(),
                    foundIssueEntity.getTitle(),
                    foundIssueEntity.getDescription());
                return foundIssueDto;
            })
            .collect(Collectors.toList());

        return new IssueSearchResponse(foundIssueDtos);
    }

}
