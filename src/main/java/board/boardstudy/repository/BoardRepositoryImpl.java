package board.boardstudy.repository;

import board.boardstudy.dto.BoardSearchCondition;
import board.boardstudy.entity.Board;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import static board.boardstudy.entity.QBoard.*;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Board> findSearch(BoardSearchCondition condition, Pageable pageable) {
        QueryResults<Board> results = jpaQueryFactory
                .selectFrom(board)
                .where(
                        writerContain(condition.getWriter()),
                        subjectContain(condition.getSubject()),
                        keywordContain(condition.getKeyword())
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults() , pageable , results.getTotal());
    }

    private BooleanExpression writerContain(String writer){
        return StringUtils.hasText(writer) ? board.writer.contains(writer) : null ;
    }

    private BooleanExpression subjectContain(String subject){
        return StringUtils.hasText(subject) ? board.subject.contains(subject) : null;
    }

    private BooleanExpression keywordContain(String keyword){
        return StringUtils.hasText(keyword) ? board.subject.contains(keyword) : null;
    }
}
