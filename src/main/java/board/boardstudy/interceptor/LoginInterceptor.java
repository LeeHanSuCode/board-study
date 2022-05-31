package board.boardstudy.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);

        if(session==null || session.getAttribute("loginMember")==null){

                log.info("비로그인 사용자 입니다.");

                //비로그인 사용자의 경우 , 로그인 화면으로 이동.
                response.sendRedirect("/login?requestURI=" + requestURI);

                return false;
        }
        return true;
    }
}
