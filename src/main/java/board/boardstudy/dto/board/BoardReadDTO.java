package board.boardstudy.dto.board;

import board.boardstudy.dto.CommentReadDTO;
import board.boardstudy.dto.FileDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardReadDTO {

    public BoardReadDTO(Long boardId , Long memberId , String subject , String boardContent , String writer , LocalDateTime createdDate ,
                        Integer readCount){
        this.id = boardId;
        this.memberId = memberId;
        this.subject =subject;
        this.boardContent =boardContent;
        this.writer =writer;
        this.createdDate = createdDate;
        this.readCount =readCount;
    }

    private Long id;

    private String subject;

    private String boardContent;

    private String writer;

    private Long memberId;

    private LocalDateTime createdDate;

    private Integer readCount;


}
