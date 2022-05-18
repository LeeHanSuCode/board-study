package board.boardstudy.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
public class CommentReadDTO {

    public CommentReadDTO(Long id ,Long memberId ,String writer , String commentContent
            , LocalDateTime createdDate){
        this.id=id;
        this.memberId = memberId;
        this.writer =writer;
        this.commentContent =commentContent;
        this.createdDate = createdDate;

    }

    private Long id;
    private Long memberId;
    private String writer;
    private String commentContent;
    private LocalDateTime createdDate;


}
