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
        //패턴 검증
        if (!matcherEmail(email)) {
            return "error";
        }
        if (!username.equals(memberService.findByEmail(email).getUsername())) {
            return "error_username";
        }
        return emailService.mail(email, certificationNumber());
    }


    //인증번호 조합
    private String certificationNumber(){
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            numStr += Integer.toString(rand.nextInt(10));
        }
        return numStr;
    }


    //이메일 검증
    private boolean matcherEmail(String email){
        if(!Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$").matcher(email).matches()){
            return false;
        }
        return true;
    }


    //아이디 찾기인증번호 확인 -> 아이디 반환.
    @PostMapping("/findId/{email}")
    @ResponseBody
    public String findIdEmailPro(@PathVariable String email){
        return memberService.findByEmail(email).getUserId();
    }



    //비번 찾기 인증번호 요청
    @PostMapping("/findPw/{email}/{username}/{userId}")
    @ResponseBody
    public String findIdPhone(@PathVariable String email , @PathVariable String username , @PathVariable String userId){
        //검증
        if(!matcherEmail(email)){
            return "error";
        }
        FindDTO findMember = memberService.findByEmail(email);
        if(!username.equals(findMember.getUsername()) || !userId.equals(findMember.getUserId())){
            return "error_member";
        }
        return emailService.mail(email ,  certificationNumber());
    }

}
