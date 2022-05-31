package board.boardstudy.controller.certification;

import board.boardstudy.dto.LoginDTO;
import board.boardstudy.dto.find.FindDTO;
import board.boardstudy.entity.Member;
import board.boardstudy.service.MemberService;
import board.boardstudy.service.certification.EmailService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/email")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;
    private final MemberService memberService;

    //아이디 찾기 -> 인증번호 전송
    @PostMapping("/findId/{email}/{username}")
    @ResponseBody
    public String findIdMail(@PathVariable String email ,@PathVariable String username) {

        //이메일 형식 검증
        if (!matcherEmail(email)) {
            return "error";
        }

        //email로 회원을 조회.
        //username과 일치하는지 확인.
        if (!username.equals(memberService.findByEmail(email).getUsername())) {
            return "error_username";
        }


        return emailService.mail(email, certificationNumber());
    }


    //인증번호 조합(0~9의 숫자로 4자리 조합)
    private String certificationNumber(){
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            numStr += Integer.toString(rand.nextInt(10));
        }
        return numStr;
    }


    //이메일 형식 검증
    private boolean matcherEmail(String email){
        if(!Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$").matcher(email).matches()){
            return false;
        }
        return true;
    }


    /*클라이언트 측에서 인증번호가 일치할 경우 호출
     이메일로 회원을 조회하여 회원 아이디를 반환.
    * */
    @PostMapping("/findId/{email}")
    @ResponseBody
    public String findIdEmailPro(@PathVariable String email){
        return memberService.findByEmail(email).getUserId();
    }



    //비번 찾기 인증번호 요청
    @PostMapping("/findPw/{email}/{username}/{userId}")
    @ResponseBody
    public String findIdPhone(@PathVariable String email , @PathVariable String username , @PathVariable String userId){
        //이메일 형식 검증
        if(!matcherEmail(email)){
            return "error";
        }

        //이메일로 회원 조회.
        FindDTO findMember = memberService.findByEmail(email);

        //이름과 아이디 일치여부
        if(!username.equals(findMember.getUsername()) || !userId.equals(findMember.getUserId())){
            return "error_member";
        }

        return emailService.mail(email ,  certificationNumber());
    }

}
