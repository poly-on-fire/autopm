package pull.cruft;

import java.util.Date;

import pull.domain.TopicSignup;
import pull.domain.EmailStart;
import pull.repo.EmailLogRepo;
import pull.repo.EmailStartRepo;
import pull.repo.EmailVerifyUnsubscribeRepo;

//	TODO: Comment - one of several dev-oriented cruft pieces that are not being used but were so handy that I wanted to leave as a how-to
public class InitEmailTables {

	EmailStartRepo ecdbr = new EmailStartRepo();
	EmailVerifyUnsubscribeRepo evur = new EmailVerifyUnsubscribeRepo();
	EmailLogRepo elr = new EmailLogRepo();

	public void go() {
		ecdbr.add(new EmailStart("emailAddress", "topicKey", "170201", "admin"));
		evur.add(new TopicSignup("emailAddress", "topicKey", false, new Date().getTime()));
		elr.add(System.getenv("EMAIL_REPLYTO"), "abcdefg", System.getenv("EMAIL_FROM_NAME"), "abcdefghijkey");
	}
}
