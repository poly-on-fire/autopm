package pull.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pull.domain.EmailLog;
import pull.util.SendEmail;

@Service
public class SendEmailVerifyEmail {
	@Autowired
	SendEmail sendEmail;

	@Value("${run.naked}")
	private boolean runNaked;

	public void go(EmailLog emailLog) {
		if(runNaked) {
			return;
		}
		boolean processEmailLog = true;
		if (emailLog == null) {
			System.err.println("YOU ARE SOMEHOW ATTEMPTING TO PROCESS A NULL EMAIL LOG");
			processEmailLog = false;
		}
		if (processEmailLog && (emailLog.getTopicKey() == null || emailLog.getTopicKey().length() < 5)) {
			System.err.println("YOU ARE SOMEHOW ATTEMPTING TO PROCESS A JIVE EMAIL LOG KEY for "
					+ emailLog.getClass().getTypeName());
			processEmailLog = false;
		}
		if (processEmailLog && (emailLog.getEmailAddress() == null || emailLog.getEmailAddress().length() < 5)) {
			System.err.println(
					"YOU ARE SOMEHOW ATTEMPTING TO PROCESS A JIVE EMAIL ADDRESS of " + emailLog.getEmailAddress());
			processEmailLog = false;
		}
		if (processEmailLog) {
			try {
				String properName = emailLog.getProperName();
				if(null!=properName&&properName.trim().length()>3){
					properName = URLEncoder.encode(properName.trim(), "UTF-8");
				}
				String message = "Your email address has been verified! To view the content you have requested, click "
						+  " https://clouddancer.info/z"
						+ emailLog.getTopicKey() + "e.html?topic=" + emailLog.getTopicKey() + "&properName="
						+ properName + "&email="
						+ emailLog.getEmailAddress() + "\n\n Alternately, you may also paste the link in your browser address bar.";

				sendEmail.go("verify@clouddancer.info", "cloudDancer.info Server", emailLog.getEmailAddress(), message, "Please click to verify your email.");
			} catch (Exception e) {
				e.printStackTrace();
				//TODO handle real exception, UnsupportedEncodingException
			}
		}
	}

}
