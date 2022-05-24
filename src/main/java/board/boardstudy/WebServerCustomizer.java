package board.boardstudy;

import board.boardstudy.exception.MemberException;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class WebServerCustomizer implements
        WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        ErrorPage memberErrorPage = new ErrorPage(MemberException.class, "/myException/members");

        factory.addErrorPages(memberErrorPage);
    }
}
