package board.boardstudy.controller;

import board.boardstudy.argument.Login;
import board.boardstudy.dto.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@Login LoginDTO loginMember, Model model){


        return "/index";
    }

}
