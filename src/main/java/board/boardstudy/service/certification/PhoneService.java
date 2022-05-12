package board.boardstudy.service.certification;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PhoneService {

    public void joinCertification(String tel ,String numStr){
        String api_key = "api키";
        String api_secret = "api_secret 키";


        Message coolsms = new Message(api_key,api_secret);

        HashMap<String, String> message = message(tel, numStr);

        try {
            JSONObject obj = (JSONObject) coolsms.send(message);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            e.getMessage();
           e.printStackTrace();
        }
    }

    private HashMap<String,String> message(String tel , String numStr){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", tel);      //사용자 번호
        params.put("from", tel);    //발신전화번호 (테스트이므로 보내는이와 받는이 같게 설정)

        params.put("type", "SMS");
        params.put("text", "개인 미니 프로젝트 : 인증번호는" + "["+numStr+"]" + "입니다.");
        params.put("app_version", "test app 1.2");

        return params;

    }


}
