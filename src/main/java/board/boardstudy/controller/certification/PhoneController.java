package board.boardstudy.controller.certification;

import board.boardstudy.dto.LoginDTO;
import board.boardstudy.dto.find.FindDTO;
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
@RequestMapping("/tel")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;
    private final MemberService memberService;


    //회원가입시 전화번호 인증.
    @PostMapping("/phone/{tel}")
    @ResponseBody
    public String phoneCertification(@PathVariable String tel){

        //전화번호 형식 검증
        if(!matcherTel(tel)){
            return "error";
        }

       return phoneService.joinCertification(tel ,certificationNumber());
    }



    //아이디 찾기
    @PostMapping("/findId/{tel}/{username}")
    @ResponseBody
    public String findIdPhone(@PathVariable String tel , @PathVariable String username){
        //전화번호 형식 검증
        if(!matcherTel(tel)){
            return "error";
        }

        //전화번호로 회원 조회
        if(!username.equals( memberService.findByTel(tel).getUsername())){
            return "error_username";
        }

        return  phoneService.joinCertification(tel , certificationNumber());
    }



    /*클라이언트 측에서 인증번호가 일치할 경우 호출
    전화번호 회원 조회 -> 회원 아이디 반환.
   * */
    @PostMapping("/findId/{tel}")
    @ResponseBody
    public String findIdPhonePro(@PathVariable String tel){
        return memberService.findByTel(tel).getUserId();
    }




    //비번 찾기 인증번호 요청
    @PostMapping("/findPw/{tel}/{username}/{userId}")
    @ResponseBody
    public String findIdPhone(@PathVariable String tel , @PathVariable String username , @PathVariable String userId){
        //전화번호 형식 검증
        if(!matcherTel(tel)){
            return "error";
        }

        //회원 조회
        FindDTO findMember = memberService.findByTel(tel);

        if(!username.equals(findMember.getUsername()) || !userId.equals(findMember.getUserId())){
            return "error_member";
        }

        return phoneService.joinCertification(tel , certificationNumber());

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

        //넘어온 데이터 공백시 return
        if(bs.hasErrors()){
            return "/login/find/pw/changePw_Form";
        }

        //비밀번호&비밀번호확인 일치 여부
        if(!loginDTO.getPassword().equals(loginDTO.getPassword2())){
            bs.rejectValue("password","NotEqualsPw");
            return "/login/find/pw/changePw_Form";
        }

        memberService.changePw(loginDTO);

        return "/login/find/pw/changePw_success";
    }


    //휴대폰 형식 검증
    private boolean matcherTel(String tel){

        if(!Pattern.compile("\\d{3}-\\d{3,4}-\\d{4}").matcher(tel).matches()){
            return false;
        }
        return true;
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



}
