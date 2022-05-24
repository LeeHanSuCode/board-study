package board.boardstudy.controller;

import board.boardstudy.dto.LoginDTO;
import board.boardstudy.dto.find.FindDTO;
import board.boardstudy.entity.Member;
import board.boardstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    private final String loginFail = "존재하지 않는 회원 입니다.";


    @GetMapping
    public String login_form(Model model){
        model.addAttribute("loginDTO" , new LoginDTO());
        return "/login/login";
    }

    @PostMapping
    public String login_process(@Validated LoginDTO loginDTO, BindingResult bs , HttpServletRequest request
                                , @RequestParam(defaultValue = "/") String requestURI){
        if(bs.hasErrors()){
            return "/login/login";
        }

        Member findMember = memberService.findByUserId(loginDTO.getUserId());

        if(!findMember.getPassword().equals(loginDTO.getPassword())){
            bs.reject("NotMember","존재하지 않는 회원 입니다.");
            return "/login/login";
        }

        createSession(request,findMember);

        return "redirect:" + requestURI;
    }

    //세션 만들기.
    private void createSession(HttpServletRequest request , Member member){
        HttpSession session = request.getSession();

        LoginDTO loginMember = new LoginDTO();

        loginMember.setUserId(member.getUserId());
        loginMember.setWriter(member.getUsername());
        loginMember.setGrade(member.getMemberGrade());
        loginMember.setId(member.getId());

        session.setAttribute("loginMember" , loginMember);
    }


    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session != null){
            session.invalidate();
        }
        return "/login/logout_success";
    }


    //아아디 찾기
    @GetMapping("/findId")
    public String findId_Form(){
        return "/login/find/id/find_id";
    }

    //비밀번호 찾기
    @GetMapping("/findPassword")
    public String findPassword_Form(){
        return "/login/find/pw/find_password";
    }

    //아이디 찾기 -> telForm
    @GetMapping("/findId_tel")
    public String findId_telForm(){
        return "/login/find/id/findId_telForm";
    }

    //아이디 찾기 -> emailForm
    @GetMapping("/findId_email")
    public String findId_emailForm(){
        return "/login/find/id/findId_emailForm";
    }


    //비밀번호 찾기 ->telForm
    @GetMapping("/findPw_tel")
    public String findPw_telForm(){
        return "/login/find/pw/findPw_telForm";
    }


    //비밀번호 찾기 ->emailForm
    @GetMapping("/findPw_email")
    public String findPw_emailForm(){
        return "/login/find/pw/findPw_emailForm";
    }


}
