package pull.dbinit;

import java.util.Date;

import pull.domain.TopicSignup;
import pull.domain.EmailStart;
import pull.repo.EmailLogRepo;
import pull.repo.EmailStartRepo;
import pull.repo.EmailVerifyUnsubscribeRepo;

public class InitEmailTables {

	EmailStartRepo ecdbr = new EmailStartRepo();
	EmailVerifyUnsubscribeRepo evur = new EmailVerifyUnsubscribeRepo();
	EmailLogRepo elr = new EmailLogRepo();

	public void go() {
		ecdbr.add(new EmailStart("emailAddress", "topicKey", "170201", "admin"));
		evur.add(new TopicSignup("emailAddress", "topicKey", false, new Date().getTime()));
		elr.add("pete@couldbe.net", "abcdefg", "Pete Carapetyan", "abcdefghijkey");
	}
}
