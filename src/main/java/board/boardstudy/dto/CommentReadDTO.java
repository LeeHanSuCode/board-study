package board.boardstudy.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CommentReadDTO {

    public CommentReadDTO(Long id , String writer , String commentContent){
        this.id=id;
        this.writer =writer;
        this.commentContent =commentContent;
    }

    private Long id;
    private String writer;
    private String commentContent;

}
