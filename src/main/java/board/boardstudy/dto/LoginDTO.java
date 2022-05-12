package board.boardstudy.dto;

import board.boardstudy.entity.MemberGrade;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class LoginDTO {

    private Long id;

    @NotBlank
    private String userId;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$")
    private String password;

    private String password2;

    private String writer;

    private MemberGrade grade;



}
