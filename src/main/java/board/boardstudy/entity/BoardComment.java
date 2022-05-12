package board.boardstudy.entity;

import board.boardstudy.entity.mappedEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name="BOARD_COMMENT_SEQ_GENERATOR",
        sequenceName = "BOARD_COMMENT_SEQ",
        initialValue = 1 , allocationSize = 1
)
public class BoardComment extends BaseEntity {

    @Id @GeneratedValue(generator = "BOARD_COMMENT_SEQ_GENERATOR")
    private Long id;

    private String commentContent;

    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BOARD_ID")
    private Board board;


}
