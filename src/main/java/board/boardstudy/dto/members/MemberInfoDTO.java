package board.boardstudy.dto.members;

import board.boardstudy.entity.Member;
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
