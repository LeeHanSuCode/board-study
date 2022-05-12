package board.boardstudy.dto;

import lombok.Getter;
import lombok.Setter;

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
}
