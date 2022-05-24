package board.boardstudy.service.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EmailService{

    private final JavaMailSender javaMailSender;



    public String mail(String email ,String num){

        String from ="dlsdn857758@gmail.com";      //보내는 메일
        String to = email;
        String title = "studyBoard 인증번호 입니다.";
        String content="[인증번호]" + num + "입니다 <br /> 인증번호 확인란에 기입해주세요.";

        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper maHelper = new MimeMessageHelper(mail,true,"UTF-8");

            maHelper.setFrom(from);
            maHelper.setTo(to);
            maHelper.setSubject(title);
            maHelper.setText(content, true);

            javaMailSender.send(mail);
        }catch (MessagingException e){
            e.printStackTrace();
            e.getMessage();
        }

        return num;
    }
}
