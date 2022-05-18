package board.boardstudy.entity;

import board.boardstudy.entity.mappedEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name="COMMENT_SEQ_GENERATOR",
        sequenceName = "COMMENT_SEQ",
        initialValue = 1 , allocationSize = 1
)
public class Comments extends BaseEntity {

    protected Comments(){}

    public Comments(String commentContent , String writer , Board board , Member member){
        this.commentContent = commentContent;
        this.writer =writer;
        this.board = board;
        this.member = member;

        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @Id @GeneratedValue(generator = "COMMENT_SEQ_GENERATOR")
    private Long id;

    private String commentContent;

    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BOARD_ID")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    //변경 메서드
    public void changeContent(String content){
        this.updatedDate = LocalDateTime.now();
        this.commentContent = content;
    }

}
