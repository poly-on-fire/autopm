package pull.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;

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
        HtmlEmail email = new HtmlEmail();
        email.setHostName(emailHostName);
        email.setSmtpPort(emailPort);
        email.setAuthenticator(new DefaultAuthenticator(emailServer, emailAuth));
        email.setSSLOnConnect(true);
        email.setFrom(fromEmail, fromName);
        email.setSubject(subject);
//        email.setHtmlMsg(msg);
        email.setTextMsg(msg);
//        email.setTextMsg("Your email client does not support HTML messages"); What is this?
        email.addTo(to);
        email.send();
    }

    public void goWReplyTo(String replyTo, String fromEmail, String fromName, String to, String msg, String subject) throws Exception {
        InternetAddress internetAddress = new InternetAddress(replyTo);
        Collection<InternetAddress> replyTos = new ArrayList<InternetAddress>();
        replyTos.add(internetAddress);

        HtmlEmail email = new HtmlEmail();
        email.setHostName(emailHostName);
        email.setSmtpPort(emailPort);
        email.setAuthenticator(new DefaultAuthenticator(emailServer, emailAuth));
        email.setSSLOnConnect(true);
        email.setFrom(fromEmail, fromName);
        email.setSubject(subject);
//        email.setHtmlMsg(msg);
        email.setTextMsg(msg);
//        email.setTextMsg("Your email client does not support HTML messages"); What is this?
        email.setReplyTo(replyTos);
        email.addTo(to);
        email.send();
    }


    public void devTest(String replyTo, String emailHostName, int emailPort, String emailServer, String emailAuth, String fromEmail,
                        String fromName, String to, String msg, String subject) throws Exception {
        this.emailHostName = emailHostName;
        this.emailPort = emailPort;
        this.emailServer = emailServer;
        this.emailAuth = emailAuth;
        goWReplyTo(replyTo, fromEmail, fromName, to, msg, subject);
    }
}
