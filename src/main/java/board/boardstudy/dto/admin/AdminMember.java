package board.boardstudy.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AdminMember {

    public AdminMember(Long id , String username , String tel , String email ,  String userId , String root
    ,LocalDateTime createdDate , LocalDateTime updatedDate){
        this.id = id;
        this.username = username;
        this.tel = tel;
        this.email = email;
        this.userId = userId;
        this.root = root;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    private Long id;
    private String username;
    private String tel;
    private String email;
    private String password;
    private String userId;
    private String root;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
