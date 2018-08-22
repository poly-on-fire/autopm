package pull.service.manual;

import pull.util.SendEmail;

public class EmailManualDev {
    public static void main(String[] args) throws Exception{
        new SendEmail().devTest(System.getenv("EMAIL_REPLYTO"),
                System.getenv("EMAIL_HOST_NAME"),
                Integer.valueOf(System.getenv("EMAIL_PORT")),
                System.getenv("EMAIL_SERVER"),
                System.getenv("EMAIL_AUTH"),
                System.getenv("EMAIL_NOREPLY"),
                System.getenv("EMAIL_FROM_NAME"),
                System.getenv("EMAIL_ADMIN"),"test message", "Test Email");
    }
}
