package board.boardstudy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class UpdateMemberDTO {

    public UpdateMemberDTO(String username ,String tel , String email
    ){
        this.username = username;
        this.email =email;
        this.tel =tel;
    }

    @NotBlank
    private Long id;

    @NotBlank
    @Size(min=2 , max = 8)
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$")
    private String password;

    private String password2;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    private String tel;
}
