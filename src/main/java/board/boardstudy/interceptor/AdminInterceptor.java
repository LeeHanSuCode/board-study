package board.boardstudy.interceptor;

import board.boardstudy.dto.LoginDTO;
import board.boardstudy.entity.MemberGrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);


        if(session==null || session.getAttribute("loginMember")==null){
            LoginDTO loginMember = (LoginDTO) session.getAttribute("loginMember");
            if(loginMember.getGrade()!= MemberGrade.MASTER) {

                log.info("관리자에 의한 접근이 아닙니다.");
                response.sendRedirect("/login?requestURI=" + requestURI);
                return false;
            }
        }
        return true;
    }
}

