package net.fullstack7.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import net.fullstack7.springboot.entity.QReply;
import net.fullstack7.springboot.entity.Reply;

public class ReplyRepositoryImpl extends QuerydslRepositorySupport implements ReplyRepository {
  public ReplyRepositoryImpl() {
    super(Reply.class);
  }

  @Override
  public List<Reply> findByBoardId(Long boardId) {
    QReply reply = QReply.reply;

    return from(reply)
      .where(reply.board.id.eq(boardId))
      .orderBy(reply.depth.asc())
      .fetch();
  }
}
