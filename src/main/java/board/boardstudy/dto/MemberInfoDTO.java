package board.boardstudy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDTO {

    public MemberInfoDTO(String username , String tel , String email){
        this.username = username;
        this.tel = tel;
        this.email = email;
    }

    private String username;
    private String tel;
    private String email;
}
