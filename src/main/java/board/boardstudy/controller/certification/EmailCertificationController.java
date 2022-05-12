package board.boardstudy.controller.certification;

import board.boardstudy.dto.LoginDTO;
import board.boardstudy.entity.Member;
import board.boardstudy.service.MemberService;
import board.boardstudy.service.certification.EmailService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/email_certification")
@RequiredArgsConstructor
@Slf4j
public class EmailCertificationController {

    private final EmailService emailService;
    private final MemberService memberService;

    //아이디 찾기 -> 인증번호 전송
    @PostMapping("/find/email/{email}/{username}")
    @ResponseBody
    public String findIdMail(@PathVariable String email ,@PathVariable String username){
        log.info("email={}" , email);
        log.info("username={}" , username);
        //패턴 검증
        if(!matcherEmail(email)){
            return "error";
        }

        Member findMember = memberService.findByEmail(email);

        if(!username.equals(findMember.getUsername())){
            return "error_username";
        }

        //인증번호 조합
        String number = certificationNumber();

        emailService.mail(email, number);

        return number;
    }
    
    //인증번호 조합
    private String certificationNumber(){
        Random rand  = new Random();
        String numStr = "";

        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        return numStr;
    }

    //이메일 검증
    private boolean matcherEmail(String email){
        Pattern pattern = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            return false;
        }
        return true;
    }

    //아이디 찾기인증번호 확인 -> 아이디 반환.
    @PostMapping("/find/email/{email}")
    @ResponseBody
    public String findIdEmailPro(@PathVariable String email){
        Member findMember = memberService.findByEmail(email);

        return findMember.getUserId();
    }


    //비번 찾기 인증번호 요청 -> 검증로직이 같이 동작(username , tel , userId)
    @PostMapping("/findPw/email/{email}/{username}/{userId}")
    @ResponseBody
    public String findIdPhone(@PathVariable String email , @PathVariable String username , @PathVariable String userId){
        //검증
        if(!matcherEmail(email)){
            return "error";
        }

        Member findMember = memberService.findByEmail(email);

        if(!username.equals(findMember.getUsername()) || !userId.equals(findMember.getUserId())){
            return "error_member";
        }

        String numStr = certificationNumber();

        emailService.mail(email , numStr);

        return numStr;
    }

}
