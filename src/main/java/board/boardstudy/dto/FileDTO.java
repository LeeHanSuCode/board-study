package board.boardstudy.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class FileDTO {
    public FileDTO(Long id, String uploadFilename , String serverFilename){
        this.id = id;
        this.uploadFilename = uploadFilename;
        this.serverFilename = serverFilename;
    }

    private Long id;
    private String uploadFilename;
    private String serverFilename;

    private List<MultipartFile> mFiles;
}
