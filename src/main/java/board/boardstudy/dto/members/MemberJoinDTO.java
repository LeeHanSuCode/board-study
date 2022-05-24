package board.boardstudy.dto.members;

import board.boardstudy.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;


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

    @AssertTrue
    private boolean checkDoubleId;

    @AssertTrue
    private boolean checkPhone;


    //MemberJoinDTO -> Member
    public Member changeToMemberEntity(MemberJoinDTO joinMemberDTO){

        String temp;

        if(joinMemberDTO.getKnownRoot() == null){
            temp = "선택 안함";
        }else{
            temp = joinMemberDTO.getKnownRoot();
        }

        return new Member(joinMemberDTO.getUsername() , joinMemberDTO.getUserId()
                , joinMemberDTO.getPassword() , joinMemberDTO.getEmail() , joinMemberDTO.getTel() ,temp);
    }


}
