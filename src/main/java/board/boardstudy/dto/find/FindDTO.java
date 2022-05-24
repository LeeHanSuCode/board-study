package board.boardstudy.dto.find;

import board.boardstudy.dto.FileDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class FindDTO {

    public FindDTO(){
        this("없음","없음","없음","없음");
    }

    public FindDTO(String userId ,String username ,String tel , String email){
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.tel = tel;
    }

    private String userId;
    private String username;
    private String tel;
    private String email;
}
