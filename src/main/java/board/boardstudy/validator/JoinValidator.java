package board.boardstudy.validator;


import board.boardstudy.dto.JoinMemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class JoinValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinMemberDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinMemberDTO joinMemberDTO = (JoinMemberDTO)target;

        //중복 확인을 안하고 넘어왔을 경우 에러 추가.
        if(joinMemberDTO.isCheckDoubleId()==false){
            errors.rejectValue("checkDoubleId" ,"NotCheckedId");
        }
        //휴대폰 인증 안했을 경우.
        if(joinMemberDTO.isCheckPhone()==false){
            errors.rejectValue("checkPhone" ,"NotCheckedPhone");
        }

        //비밀번호가 일치하지 않을 경우 추가.
        if(!joinMemberDTO.getPassword().equals(joinMemberDTO.getPassword2())){
            errors.rejectValue("password" ,"NotEqualsPw");
        }
    }
}
