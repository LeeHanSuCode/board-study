package board.boardstudy;

import board.boardstudy.argument.LoginMemberArgumentResolver;
import board.boardstudy.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
       resolvers.add(new LoginMemberArgumentResolver());
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login","/login/logout","/members/join","/*.ico","/error","/board","/board/read/**",
                        "/image/**","/login/findId","/login/findPassword" , "/login/findId_tel" , "/login/findId_email","/login/findPw_tel","/login/findPw_email",
                        "/email_certification/**","/certification/**");
    }
}
