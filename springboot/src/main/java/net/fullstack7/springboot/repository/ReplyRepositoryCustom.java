package net.fullstack7.springboot.repository;

import java.util.List;
import net.fullstack7.springboot.entity.Reply;

public interface ReplyRepositoryCustom {
  List<Reply> findByBoardId(Long boardId);
}