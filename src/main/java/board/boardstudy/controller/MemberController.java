package board.boardstudy.controller;

import board.boardstudy.argument.Login;
import board.boardstudy.dto.members.MemberJoinDTO;
import board.boardstudy.dto.LoginDTO;
import board.boardstudy.dto.members.MemberUpdateDTO;
import board.boardstudy.entity.Member;
import board.boardstudy.service.MemberService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    //회원가입화면
    @GetMapping("/join")
    public String joinForm(Model model){
        model.addAttribute("memberJoinDTO" , new MemberJoinDTO());
        return "/members/joinForm";
    }


    //회원가입
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute MemberJoinDTO memberJoinDTO, BindingResult bs){

        if(bs.hasErrors()){
            return "/members/joinForm";
        }

        //비밀번호,비밀번호 확인 일치 여부.
        isEqualsPw(memberJoinDTO.getPassword(),memberJoinDTO.getPassword2(),bs);

        if(bs.hasErrors()){
            memberJoinDTO.setCheckPhone(false);
            return "/members/joinForm";
        }

        memberService.join(memberJoinDTO.changeToMemberEntity(memberJoinDTO));

        return "redirect:/";
    }



    //회원 상세 정보
    @GetMapping("/info")
    public String info(@Login LoginDTO loginDTO, Model model){

        Member findMember = memberService.findById(loginDTO.getId());

        model.addAttribute("memberInfoDTO" ,new MemberUpdateDTO(findMember.getUsername(),
                findMember.getTel() ,findMember.getEmail()));

        return "/members/memberInfo";
    }



    //회원 수정
    @GetMapping("/update")
    public String updateForm(@Login LoginDTO loginDTO , Model model){
        Member findMember = memberService.findById(loginDTO.getId());

        model.addAttribute("memberUpdateDTO" ,new MemberUpdateDTO(findMember.getUsername(),
                findMember.getTel() ,findMember.getEmail()));

        return "/members/updateForm";
    }



    //회원 수정
    @PostMapping("/update")
    public String update(@Validated @ModelAttribute MemberUpdateDTO memberUpdateDTO , BindingResult bs ,
                         @Login LoginDTO loginDTO){

        if(bs.hasErrors()){
            return "/members/updateForm";
        }
        //비밀번호,비밀번호 확인 일치 여부.
        isEqualsPw(memberUpdateDTO.getPassword(),memberUpdateDTO.getPassword2(),bs);

        if(bs.hasErrors()){
            return "/members/updateForm";
        }
        memberService.updateMember(loginDTO.getId() , memberUpdateDTO);

        return "redirect:/members/info";
    }


    //비밀번호 확인 일치 여부
    private void isEqualsPw(String password , String password2 , BindingResult bs){
        if(!password.equals(password2)) {
            bs.rejectValue("password", "NotEquals", "비밀번호가 일치하지 않습니다.");
        }
    }


    //아이디 중복확인 요청처리
    @GetMapping("/checkUserId/{userId}")
    @ResponseBody
    public String checkUserId(@PathVariable String userId){
        return memberService.checkUserId(userId);
    }


    //회원 삭제
    @GetMapping("/remove")
    public String remove(@Login LoginDTO loginDTO , HttpServletRequest request){
        memberService.removeMember(loginDTO.getId());

        if(request.getSession()!=null){
            request.getSession().invalidate();
        }

        return "/members/remove_success";
    }



}
