package board.boardstudy.dto;

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


}
