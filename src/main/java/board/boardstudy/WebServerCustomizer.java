package board.boardstudy;

import board.boardstudy.exception.BoardException;
import board.boardstudy.exception.CommentsException;
import board.boardstudy.exception.FileException;
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

        ErrorPage boardErrorPage = new ErrorPage(BoardException.class, "/myException/board");

        ErrorPage fileErrorPage = new ErrorPage(FileException.class, "/myException/file");

        ErrorPage commentsErrorPage = new ErrorPage(CommentsException.class,"/myException/comments");



        factory.addErrorPages(memberErrorPage);
        factory.addErrorPages(boardErrorPage);
        factory.addErrorPages(fileErrorPage);
        factory.addErrorPages(commentsErrorPage);
    }
}
