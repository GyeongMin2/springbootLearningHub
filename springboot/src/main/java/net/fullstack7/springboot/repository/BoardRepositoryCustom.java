package net.fullstack7.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import net.fullstack7.springboot.entity.Board;

public interface BoardRepositoryCustom {
    Page<Board> search(String searchType, String keyword, Pageable pageable);
    Page<Board> searchAndSort(String searchType, String keyword, Pageable pageable, String sortType, String sortOption);
}