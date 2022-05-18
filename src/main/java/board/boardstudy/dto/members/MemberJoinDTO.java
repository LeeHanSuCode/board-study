package board.boardstudy.dto.members;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//1.entity에는 setter를 열어두지 않기위해.
//2.entity를 노출시키지 않기 위해.

@Setter @Getter
public class MemberJoinDTO {


    @NotBlank
    @Size(min=2 , max = 4)
    private String username;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]{8,20}")
    private String userId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$")
    private String password;

    private String password2;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    private String tel;

    private String knownRoot;

    private boolean checkDoubleId;

    private boolean checkPhone;


}
