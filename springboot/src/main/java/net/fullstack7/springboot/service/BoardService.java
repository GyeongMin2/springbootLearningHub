package net.fullstack7.springboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.fullstack7.springboot.dto.BoardDTO;
import net.fullstack7.springboot.dto.SearchDTO;
import net.fullstack7.springboot.entity.Board;
import net.fullstack7.springboot.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {
    
    private final BoardRepository boardRepository;
    
    public Long create(BoardDTO dto) {
        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return boardRepository.save(board).getId();
    }
    
    public BoardDTO findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        return convertToDTO(board);
    }
    
    public void update(Long id, BoardDTO dto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        board.update(dto.getTitle(), dto.getContent());
        boardRepository.save(board);
    }
    
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
    
    private BoardDTO convertToDTO(Board board) {
        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }
    
    public Page<BoardDTO> search(SearchDTO searchDTO, Pageable pageable) {
        return boardRepository.search(
            searchDTO.getSearchType(),
            searchDTO.getKeyword(),
            pageable
        ).map(board -> convertToDTO(board));
    }
    
    public Page<BoardDTO> searchAndSort(SearchDTO searchDTO, Pageable pageable, String sortType, String sortOrder) {
        return boardRepository.searchAndSort(
            searchDTO.getSearchType(),
            searchDTO.getKeyword(),
            pageable,
            sortType,
            sortOrder
        ).map(board -> convertToDTO(board));
    }
}