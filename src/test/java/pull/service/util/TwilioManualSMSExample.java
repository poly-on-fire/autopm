package pull.service.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioManualSMSExample {

    // Find your Account Sid and Token at twilio.com/user/account
    public static final String TWILIO_ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String TWILIO_AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String TWILIO_PHONE_NUMBER = System.getenv("TWILIO_PHONE_NUMBER");
    public static final String TWILIO_TEST_PHONE_NUMBER = System.getenv("TWILIO_TEST_PHONE_NUMBER");

    public static void main(String[] args) {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(TWILIO_TEST_PHONE_NUMBER),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                "This is the ship that made the Kessel Run in fourteen parsecs?").create();
        System.out.println(message.getSid());
    }
}