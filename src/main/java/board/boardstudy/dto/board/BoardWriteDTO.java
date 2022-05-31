package board.boardstudy.dto.board;

import board.boardstudy.entity.Board;
import board.boardstudy.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
public class BoardWriteDTO {

    @NotBlank
    private String subject;

    @NotBlank
    private String boardContent;

    private List<MultipartFile> files;



    //BoardWriteDTO -> Board
    public Board changeToBoard(Member member) {
        return new Board(this.getSubject(),
                member.getUsername(), this.getBoardContent(), member);
    }


}
