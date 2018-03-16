package pull.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {
    @Value("${EMAIL_HOST_NAME}")
    private String emailHostName;
    @Value("${EMAIL_PORT}")
    private int emailPort;
    @Value("${EMAIL_SERVER}")
    private String emailServer;
    @Value("${EMAIL_AUTH}")
    private String emailAuth;

    public void go(String fromEmail, String fromName, String to, String msg, String subject) throws Exception {
        System.err.println("SENDING EMAIL " + subject + " EMAIL AUTH: "+ emailAuth);
        HtmlEmail email = new HtmlEmail();
        email.setHostName(emailHostName);
        email.setSmtpPort(emailPort);
        email.setAuthenticator(new DefaultAuthenticator(emailServer, emailAuth));
        email.setSSLOnConnect(true);
        email.setFrom(fromEmail, fromName);
        email.setSubject(subject);
        email.setHtmlMsg(msg);
        email.setTextMsg("Your email client does not support HTML messages");
        email.addTo(to);
        email.send();
    }

    public void devTest(String emailHostName, int emailPort, String emailServer, String emailAuth, String fromEmail,
                        String fromName, String to, String msg, String subject) throws Exception {
        this.emailHostName = emailHostName;
        this.emailPort = emailPort;
        this.emailServer = emailServer;
        this.emailAuth = emailAuth;
        go(fromEmail, fromName, to, msg, subject);
    }
}
