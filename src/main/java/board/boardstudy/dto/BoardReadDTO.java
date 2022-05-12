package board.boardstudy.dto;

import board.boardstudy.entity.FileStore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardReadDTO {

    public BoardReadDTO(Long id,String subject , String boardContent , String writer , LocalDateTime createdDate ,
                        Integer readCount){
        this.id = id;
        this.subject =subject;
        this.boardContent =boardContent;
        this.writer =writer;
        this.createdDate = createdDate;
        this.readCount =readCount;
    }

    private Long id;

    @NotBlank
    private String subject;
    @NotBlank
    private String boardContent;

    private String writer;
    private LocalDateTime createdDate;

    private Integer readCount;

    private List<MultipartFile> files;
}
