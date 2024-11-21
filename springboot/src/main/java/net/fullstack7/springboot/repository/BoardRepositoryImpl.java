package net.fullstack7.springboot.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import net.fullstack7.springboot.entity.Board;
import net.fullstack7.springboot.entity.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardRepositoryImpl extends QuerydslRepositorySupport implements BoardRepositoryCustom {
    
    public BoardRepositoryImpl() {
        super(Board.class);
    }
    
    private BooleanBuilder createSearchBuilder(String searchType, String keyword, QBoard board) {
        BooleanBuilder builder = new BooleanBuilder();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            switch (searchType) {
                case "title":
                    builder.and(board.title.contains(keyword));
                    break;
                case "content":
                    builder.and(board.content.contains(keyword));
                    break;
                case "writer":
                    builder.and(board.writer.contains(keyword));
                    break;
                case "all":
                    builder.and(board.title.contains(keyword)
                            .or(board.content.contains(keyword))
                            .or(board.writer.contains(keyword)));
                    break;
            }
        }
        return builder;
    }
    
    @Override
    public Page<Board> search(String searchType, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        BooleanBuilder builder = createSearchBuilder(searchType, keyword, board);
        
        List<Board> content = from(board)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.id.desc())
                .fetch();
                
        long total = from(board)
                .where(builder)
                .fetchCount();
                
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Board> searchAndSort(String searchType, String keyword, Pageable pageable, String sortType, String sortOrder) {
        QBoard board = QBoard.board;
        BooleanBuilder builder = createSearchBuilder(searchType, keyword, board);
        JPAQuery<Board> query = (JPAQuery<Board>) from(board)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        switch (sortType) {
            case "createdAt":
                query.orderBy(sortOrder.equals("asc") ? board.id.asc() : board.id.desc());
                break;
            case "title":
                query.orderBy(sortOrder.equals("asc") ? board.title.asc() : board.title.desc());
                break;
            case "writer":
                query.orderBy(sortOrder.equals("asc") ? board.writer.asc() : board.writer.desc());
                break;
            default:
                query.orderBy(board.id.desc());
                break;
        }
        List<Board> content = query
                .fetch();
        
        long total = from(board)
                .where(builder)
                .fetchCount();
        
        return new PageImpl<>(content, pageable, total);
    }
}
