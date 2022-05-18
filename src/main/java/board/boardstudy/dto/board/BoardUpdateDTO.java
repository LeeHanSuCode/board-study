package board.boardstudy.dto.board;

import board.boardstudy.dto.FileDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardUpdateDTO {

    public BoardUpdateDTO(Long id ,String writer,String subject , String content){
        this.writer =writer;
        this.id = id;
        this.subject =subject;
        this.content = content;
    }

    private String writer;
    private Long id;

    @NotBlank
    private String subject;
    @NotBlank
    private String content;

    private List<MultipartFile> files;

    private List<Long> removeFiles = new ArrayList<>();



}
