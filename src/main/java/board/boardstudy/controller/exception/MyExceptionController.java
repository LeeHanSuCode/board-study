package board.boardstudy.controller.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myException")
public class MyExceptionController {

    @GetMapping("/members")
    public String memberErrors(){
        return "/error/members";
    }

}

//전화번호와 이메일로 찾는 것은 restful방식이다 . 고로 -> excetpion처리보다는 따로 별도의 코드를
//내보내는 것이 맞을 듯 싶다.