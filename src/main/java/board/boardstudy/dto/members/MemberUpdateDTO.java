package board.boardstudy.dto.members;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.*;



@Getter @Setter
public class MemberUpdateDTO {

    public MemberUpdateDTO(String username , String tel , String email
    ){
        this.username = username;
        this.email =email;
        this.tel =tel;
    }



    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$")
    private String password;

    private String password2;


    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    @NotBlank
    private String tel;
}
