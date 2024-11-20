package net.fullstack7.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.springboot.service.BoardService;
import net.fullstack7.springboot.dto.BoardDTO;
import net.fullstack7.springboot.dto.SearchDTO;

@Log4j2
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;
    
    // 게시글 목록 페이지
    @GetMapping
    public String list(@ModelAttribute SearchDTO searchDTO, Model model,
                  @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) 
                  Pageable pageable) {
        Page<BoardDTO> boardPage = boardService.search(searchDTO, pageable);
        
        // 페이지 그룹 계산
        int currentPage = boardPage.getNumber() + 1;
        int totalPages = boardPage.getTotalPages();
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(totalPages, startPage + 4);
        
        model.addAttribute("boards", boardPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("searchDTO", searchDTO);
        
        return "board/list";
    }
    
    // 게시글 작성 페이지
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }
    
    // 게시글 작성 처리
    @PostMapping("/write")
    public String write(BoardDTO dto) {
        log.info("dto: {}", dto);
        boardService.create(dto);
        return "redirect:/board";
    }
    
    // 게시글 상세 페이지
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.findById(id));
        return "board/detail";
    }
    
    // 게시글 수정 페이지
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.findById(id));
        return "board/edit";
    }
    
    // 게시글 수정 처리
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, BoardDTO dto) {
        boardService.update(id, dto);
        return "redirect:/board/" + id;
    }
    
    // 게시글 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board";
    }
}