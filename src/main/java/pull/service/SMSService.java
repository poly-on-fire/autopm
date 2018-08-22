package pull.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSService {
    @Value("${TWILIO_ACCOUNT_SID}")
    private static String TWILIO_ACCOUNT_SID;
    @Value("${TWILIO_AUTH_TOKEN}")
    private static String TWILIO_AUTH_TOKEN;
    @Value("${TWILIO_PHONE_NUMBER}")
    private String TWILIO_PHONE_NUMBER;

    public void main(String to, String body) {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(to),
                new PhoneNumber(TWILIO_PHONE_NUMBER),body).create();
        //TODO log or write to db
//        System.out.println(message.getSid());
    }
}