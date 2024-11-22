package net.fullstack7.springboot.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reply")
@Data
public class Reply extends BoardEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String writer;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private Integer depth;

  @ManyToOne
  @JoinColumn(name = "board_id")
  private Board board;
}
