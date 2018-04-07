package pull.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pull.domain.EmailLog;
import pull.domain.TopicLookup;
import pull.util.Db;
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
		//TODO this looks crazy what about using the front end's regex here?
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
				//TODO this looks crazy here too, what about using the front end's regex here?
				if(null!=properName&&properName.trim().length()>3){
					properName = URLEncoder.encode(properName.trim(), "UTF-8");
				}
                sendAppropriateEmailVerify(emailLog);
			} catch (Exception e) {
				e.printStackTrace();
				//TODO handle real exception, UnsupportedEncodingException
			}
		}
	}

	private void sendAppropriateEmailVerify(EmailLog emailLog) {
		DatabaseReference ref = Db.coRef("/topicLookup/" + emailLog.getTopicKey());
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				String message = null;
				if (null != dataSnapshot.getValue()) {
					TopicLookup topicLookup = dataSnapshot.getValue(TopicLookup.class);
					if(topicLookup.typeIndex=="2"){
						message = getDualWebPageMessage(emailLog);
					}else {
						message = getSingleWebPageMessage(emailLog);
					}
					try {
						sendEmail.go("verify@clouddancer.info", "cloudDancer.info Server", emailLog.getEmailAddress(), message, "Please click to verify your email.");
					}catch (Exception e){
						System.out.println(e.getMessage());
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
			}
		});
	}

    private String getSingleWebPageMessage(EmailLog emailLog) {
        String message = "Your email address has been verified! To view the content you have requested, click "
                +  " https://pullmodel.com/logins?topicKey="
                + emailLog.getTopicKey() + "&properName="
                + emailLog.getProperName() + "&emailAddress="
                + emailLog.getEmailAddress() + "\n\n Alternately, you may also paste the link in your browser address bar.";
        return message;
    }

    private String getDualWebPageMessage(EmailLog emailLog) {
        String message = "Your email address has been verified! To view the content you have requested, click "
                +  " https://clouddancer.info/z"
                + emailLog.getTopicKey() + "e.html?topic=" + emailLog.getTopicKey() + "&properName="
                + emailLog.getProperName() + "&email="
                + emailLog.getEmailAddress() + "\n\n Alternately, you may also paste the link in your browser address bar.";
        return message;
    }
}
