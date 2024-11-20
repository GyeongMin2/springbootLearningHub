package net.fullstack7.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.fullstack7.springboot.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b WHERE " +
           "(:searchType = 'title' AND b.title LIKE %:keyword%) OR " +
           "(:searchType = 'content' AND b.content LIKE %:keyword%) OR " +
           "(:searchType = 'writer' AND b.writer LIKE %:keyword%) OR " +
           "(:searchType = 'all' AND (b.title LIKE %:keyword% OR b.content LIKE %:keyword% OR b.writer LIKE %:keyword%)) " +
           "ORDER BY b.id DESC")
    Page<Board> search(@Param("searchType") String searchType, 
                      @Param("keyword") String keyword, 
                      Pageable pageable);
} 