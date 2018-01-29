package pull.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pull.domain.TopicEmails;
import pull.domain.EmailStart;
import pull.repo.EmailStartRepo;

@Service
public class NewTopicEmailsRequest {
	@Autowired
	EmailStartRepo emailStartRepo;

	public void make(TopicEmails topicEmails) {
		String yyMMdd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String emails = topicEmails.getEmails();
		if (null != emails && emails.length()>10) {
			Map<String, String> emailMap = EmailSplitService.splitCanonicalEmailList(emails);
			emailMap.keySet().stream().forEach(key -> {
				emailStartRepo.add(new EmailStart(key, emailMap.get(key), yyMMdd, "SYSTEM"));
			});
		}
	}
}
