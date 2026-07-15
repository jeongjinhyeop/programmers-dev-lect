package org.example.createjoinbyjpa.domain.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.createjoinbyjpa.domain.entity.Board;
import org.example.createjoinbyjpa.domain.entity.QBoard;
import org.example.createjoinbyjpa.domain.entity.QComment;
import org.example.createjoinbyjpa.domain.entity.QMember;
import org.example.createjoinbyjpa.dto.BoardAuthorStatsResponseDto;
import org.example.createjoinbyjpa.dto.BoardListItemResponseDto;
import org.example.createjoinbyjpa.dto.BoardSearchRequestDto;
import org.example.createjoinbyjpa.dto.QBoardAuthorStatsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private JPAQueryFactory queryFactory;
    private static final QBoard board = QBoard.board;
    private static final QComment comment = QComment.comment;
    private static final QMember member = QMember.member;

    @Override
    public Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto condition, Pageable pageable) {

        List<BoardListItemResponseDto> content = queryFactory.select(
                Projections.constructor(
                        BoardListItemResponseDto.class,
                        board.id,
                        board.title,
                        board.userId,
                        member.userName,
                        commentCountOf(board), // 서브쿼리
                        board.created
                )
        )
                .from(board)
                .leftJoin(member).on(board.userId.eq(member.userId))
                .where(
                        titleContains(condition.getTitle()),
                        userIdEquals(condition.getUserId()),
                        createdGoe(condition.getFrom()),
                        createdLoe(condition.getTo())
                )
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board)
                .where(
                        titleContains(condition.getTitle()),
                        userIdEquals(condition.getUserId()),
                        createdGoe(condition.getFrom()),
                        createdLoe(condition.getTo())

        );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private Expression<Long> commentCountOf(QBoard board) {
        return JPAExpressions
                .select(comment.count())
                .from(board)
                .where(comment.board.id.eq(board.id));
    }

    private BooleanExpression titleContains(String title) {
        return (title == null || title.isBlank()) ? null : board.title.contains(title);
    }

    private BooleanExpression userIdEquals(String userId) {
        return (userId == null || userId.isBlank()) ? null : board.userId.eq(userId);
    }

    private BooleanExpression createdGoe(LocalDate from) {
        return from == null ? null : board.created.goe(from.atStartOfDay());
    }

    private BooleanExpression createdLoe(LocalDate to) {
        return to == null ? null : board.created.loe(to.atTime(LocalTime.MAX));
    }

    @Override
    public Optional<Board> findWithComment(Long id) {
        Board result = queryFactory.selectFrom(board)
                .leftJoin(board.comments, comment)
                .where(board.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<BoardAuthorStatsResponseDto> countBoardsByAuthor(long minCount) {
        queryFactory.select(
                new QBoardAuthorStatsResponseDto(
                        board.userId,
                        member.userName,
                        board.count()
                ))
                .from(board)
                .leftJoin(member).on(board.userId.eq(member.userId))
                .groupBy(board.userId, member, member.userName)
                .having(board.count().goe(minCount))
                .orderBy(board.count().desc())
                .fetch();

        return List.of();
    }
}
