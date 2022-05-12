package board.boardstudy.argument;

import board.boardstudy.dto.LoginDTO;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isHasLogin = parameter.hasParameterAnnotation(Login.class);

        boolean isLoginDTO = LoginDTO.class.isAssignableFrom(parameter.getParameterType());

        return isHasLogin && isLoginDTO;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request =(HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);

        if(session == null){
            return null;
        }

        return session.getAttribute("loginMember");
    }
}
