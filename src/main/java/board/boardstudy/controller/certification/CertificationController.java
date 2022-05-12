package board.boardstudy.controller.certification;

import board.boardstudy.dto.LoginDTO;
import board.boardstudy.entity.Member;
import board.boardstudy.service.MemberService;
import board.boardstudy.service.certification.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/certification")
@RequiredArgsConstructor
public class CertificationController {

    private final PhoneService phoneService;
    private final MemberService memberService;

    @PostMapping("/phone/{tel}")
    @ResponseBody
    public String phoneCertification(@PathVariable String tel){
        //검증
        if(!matcherTel(tel)){
            return "error";
        }
        //인증번호 조합
        String numStr = certificationNumber();

        phoneService.joinCertification(tel , numStr);

        return numStr;
    }

    //아이디 찾기 인증번호 요청 -> 검증로직이 같이 동작(username , tel).
    @PostMapping("/find/phone/{tel}/{username}")
    @ResponseBody
    public String findIdPhone(@PathVariable String tel , @PathVariable String username){
        //검증
        if(!matcherTel(tel)){
            return "error";
        }

        Member findMember = memberService.findByTel(tel);

        if(!username.equals(findMember.getUsername())){
            return "error_username";
        }

        String numStr = certificationNumber();

        phoneService.joinCertification(tel , numStr);

        return numStr;
    }

    //아이디 찾기인증번호 확인 -> 아이디 반환.
    @PostMapping("/find/phone/{tel}")
    @ResponseBody
    public String findIdPhonePro(@PathVariable String tel){
        Member findMember = memberService.findByTel(tel);

        return findMember.getUserId();
    }


    //비번 찾기 인증번호 요청 -> 검증로직이 같이 동작(username , tel , userId)
    @PostMapping("/findPw/phone/{tel}/{username}/{userId}")
    @ResponseBody
    public String findIdPhone(@PathVariable String tel , @PathVariable String username , @PathVariable String userId){
        //검증
        if(!matcherTel(tel)){
            return "error";
        }

        Member findMember = memberService.findByTel(tel);

        if(!username.equals(findMember.getUsername()) || !userId.equals(findMember.getUserId())){
            return "error_member";
        }

        String numStr = certificationNumber();

        phoneService.joinCertification(tel , numStr);

        return numStr;
    }

    //비밀번호 변경 폼 이동.
    @GetMapping("/changePw/{userId}")
    public String changePwForm(@PathVariable String userId , Model model){
        LoginDTO login = new LoginDTO();
        login.setUserId(userId);
        model.addAttribute("loginDTO", login);
        return "/login/find/pw/changePw_Form";
    }

    //비밀번호 변경 로직
    @PostMapping("/changePw")
    public String changePwPro(@Validated LoginDTO loginDTO , BindingResult bs){
        if(bs.hasErrors()){
            return "/login/find/pw/changePw_Form";
        }
        //NotEqualsPw
        if(!loginDTO.getPassword().equals(loginDTO.getPassword2())){
            bs.rejectValue("password","NotEqualsPw");
            return "/login/find/pw/changePw_Form";
        }

        memberService.changePw(loginDTO);

        return "/login/find/pw/changePw_success";
    }


    //휴대폰 형식 검증
    private boolean matcherTel(String tel){
        Pattern pattern = Pattern.compile("\\d{3}-\\d{3,4}-\\d{4}");
        Matcher matcher = pattern.matcher(tel);

        if(!matcher.matches()){
            return false;
        }
        return true;
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



}
