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
import java.util.Optional;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private String loginFail = "존재하지 않는 회원 입니다.";


    //로그인 화면으로 이동.
    @GetMapping
    public String login_form(Model model){
        model.addAttribute("loginDTO" , new LoginDTO());
        return "/login/login";
    }


    /*로그인 처리

     */
    @PostMapping
    public String login_process(@Validated LoginDTO loginDTO, BindingResult bs , HttpServletRequest request
                                , @RequestParam(defaultValue = "/") String requestURI){

        //로그인시 공백으로 입력된 것을 방지.
        if(bs.hasErrors()){
            return "/login/login";
        }

        //넘어온 아이디로 회원 조회
        Optional<Member> member = memberService.loginFindUserId(loginDTO.getUserId());


        if(member.isEmpty()){       //아이디와 일치하는 회원이 존재하지 않을 경우.
            bs.reject("NotMember","존재하지 않는 회원 입니다.");
            return "/login/login";

        }else{
                                   //아이디는 있지만 , 비밀번호가 틀린 경우.
            Member findMember = member.get();

            if(!findMember.getPassword().equals(loginDTO.getPassword())){
                bs.reject("NotMember","존재하지 않는 회원 입니다.");
                return "/login/login";
            }
                                    //아이디,비밀번호 모두 일치 시 , 세션 생성과 로그인 처리.
            createSession(request,findMember);
        }

        return "redirect:" + requestURI;
    }


    //세션 생성.
    private void createSession(HttpServletRequest request , Member member){
        HttpSession session = request.getSession();

        session.setAttribute("loginMember" , new LoginDTO(member.getId() , member.getUserId() , member.getUsername()));
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


    //아아디 찾기 화면으로 이동
    @GetMapping("/findId")
    public String findId_Form(){
        return "/login/find/id/find_id";
    }

    //비밀번호 찾기 화면으로 이동
    @GetMapping("/findPassword")
    public String findPassword_Form(){
        return "/login/find/pw/find_password";
    }

    //아이디 찾기 화면 -> 전화번호로 찾기 화면.
    @GetMapping("/findId_tel")
    public String findId_telForm(){
        return "/login/find/id/findId_telForm";
    }

    //아이디 찾기 -> 이메일로 찾기 화면.
    @GetMapping("/findId_email")
    public String findId_emailForm(){
        return "/login/find/id/findId_emailForm";
    }


    //비밀번호 찾기 화면 -> 전화번호로 찾기 화면.
    @GetMapping("/findPw_tel")
    public String findPw_telForm(){
        return "/login/find/pw/findPw_telForm";
    }


    //비밀번호 찾기 -> 이메일로 찾기 화면.
    @GetMapping("/findPw_email")
    public String findPw_emailForm(){
        return "/login/find/pw/findPw_emailForm";
    }


}
