package pull.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pull.domain.EmailOut;
import pull.domain.EmailStart;
import pull.cruft.FetchSendEmailRepo;
import pull.util.SendEmail;

@Service
public class DailyEmail {
	@Autowired
	private TopicDayService topicDayService;
	@Autowired
	SendEmail sendEmail;
	@Value("${EMAIL_FROM}")
	private String emailFrom;

	public void send() {
		/*
		 * This should seem confusing because it is getting a list of emails for
		 * one day for one topic for one email address. Although this is
		 * NEVER OK to have more than one email per topic per email address, 
		 * it is still physically possible, so that is what has to be done.
		 */
		topicDayService.printTopicDaysMap();
		Collection<EmailStart> emailStarts = (Collection<EmailStart>) topicDayService.getEmailStarts().values();
		for (EmailStart emailStart : emailStarts) {
			List<EmailOut> emailsOut = topicDayService.todaysEmail(emailStart, LocalDate.now());
			for (EmailOut emailOut : emailsOut) {
				new FetchSendEmailRepo(emailFrom, emailOut, emailStart, sendEmail);
			}
		}
	}

}
