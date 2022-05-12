package board.boardstudy.controller;

import board.boardstudy.argument.Login;
import board.boardstudy.dto.JoinMemberDTO;
import board.boardstudy.dto.LoginDTO;
import board.boardstudy.dto.MemberInfoDTO;
import board.boardstudy.dto.UpdateMemberDTO;
import board.boardstudy.entity.Member;
import board.boardstudy.service.MemberService;
import board.boardstudy.validator.JoinValidator;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final JoinValidator joinValidator;
    private final MemberService memberService;

    @InitBinder("joinMemberDTO")
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(joinValidator);
    }

    //회원가입화면
    @GetMapping("/join")
    public String joinForm(Model model){
        model.addAttribute("joinMemberDTO" , new JoinMemberDTO());
        return "/members/joinForm";
    }


    //회원가입
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute JoinMemberDTO joinMemberDTO, BindingResult bs){

        if(bs.hasErrors()){
            joinMemberDTO.setCheckPhone(false);
            return "/members/joinForm";
        }

        memberService.join(changeMemberEntity(joinMemberDTO));

        return "redirect:/";
    }


    //joinDTO -> member
    public Member changeMemberEntity(JoinMemberDTO joinMemberDTO){
        String temp;
        if(joinMemberDTO.getKnownRoot() == null){
            temp = null;
        }else{
            temp = joinMemberDTO.getKnownRoot();
        }

        return new Member(joinMemberDTO.getUsername() , joinMemberDTO.getUserId()
                , joinMemberDTO.getPassword() , joinMemberDTO.getEmail() , joinMemberDTO.getTel() ,temp);
    }


    //회원 상세 정보
    @GetMapping("/info")
    public String info(@Login LoginDTO loginDTO, Model model){

        Member findMember = memberService.findById(loginDTO.getId());

        model.addAttribute("memberInfoDTO" ,changeToMemberInfoDTO(findMember));

        return "/members/memberInfo";
    }

    //Member -> MemberInfoDTO
    private MemberInfoDTO changeToMemberInfoDTO(Member member){
        return new MemberInfoDTO(member.getUsername() ,member.getTel() ,member.getEmail() );
    }

    //Member -> UpdateMemberDTO
    private UpdateMemberDTO changeToUpdateMemberDTO(Member member){
        return new UpdateMemberDTO(member.getUsername() ,member.getTel() ,member.getEmail() );
    }


    //회원 수정
    @GetMapping("/update")
    public String updateForm(@Login LoginDTO loginDTO , Model model){
        Member findMember = memberService.findById(loginDTO.getId());

        model.addAttribute("updateMemberDTO" ,changeToUpdateMemberDTO(findMember));

        return "/members/updateForm";
    }

    //회원 수정
    @PostMapping("/update")
    public String update(@Validated @ModelAttribute UpdateMemberDTO updateMemberDTO , BindingResult bs , RedirectAttributes redirectAttributes){

        return "redirect:/member/info/{id}";
    }

    //아이디 중복확인 요청처리
    @GetMapping("/checkUserId/{userId}")
    @ResponseBody
    public String checkUserId(@PathVariable String userId){
        return memberService.checkUserId(userId);
    }





}
