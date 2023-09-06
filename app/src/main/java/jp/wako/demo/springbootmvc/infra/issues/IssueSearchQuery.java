package jp.wako.demo.springbootmvc.infra.issues;

import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntityDao;
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

        // TODO: daoにselectBy()実装する
        var foundIssueEntities = this.dao.selectByProjectId(request.getProjectId());
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
