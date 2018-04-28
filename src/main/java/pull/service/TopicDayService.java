package pull.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.print.CancelablePrintJob;

import org.springframework.stereotype.Service;

import pull.domain.TopicDay;
import pull.domain.EmailMeta;
import pull.domain.EmailOut;
import pull.domain.EmailStart;

@Service
public class TopicDayService {
	/*
	 * The primary purpose of this class is to only retain and return the latest,
	 * correct version of days for each topic. Thus, it must automagically dedupe
	 * all TopicDay classes inserted, and only maintain the latest version in
	 * global memory. For example, and most importantly, if two subsequent
	 * topicDay instances had the same topic and emailKey paths, but different
	 * days, this class would only know about the latest version inserted.
	 * 
	 * If you are reading this and at first confused with what appears to be a kind
	 * of dual usage of the emailKey, you might wish to understand that this
	 * variable has two purposes. One purpose is as a return value - every
	 * TopicDay instance is consumed by associating a specific day with a
	 * specific return value, in this case the db path to the emailMeta. But this
	 * same value is also used internally to dedupe each incoming TopicDay
	 * instance. So the emailKey (db path) is also used internally as a map key, but
	 * only a substring of that path is required for uniqueness. Kinda confusing,
	 * eh? Simple, though.
	 */
	public TopicDayService() {
		super();
	}

	Map<String, Map<String, TopicDay>> topicDaysMap = new HashMap<String, Map<String, TopicDay>>();
	private Map<String, EmailStart> emailStartMap = new HashMap<String, EmailStart>();

	public void upsertEmailStart(EmailStart emailStart) {
		String key = emailStart.getTopicKey() + emailStart.getEmailAddress();
		emailStartMap.put(key, emailStart);
		/*
		 * Save this sysout for confidence building measure when you get lost and doubt
		 * that it works Since testing is so far, a shit show 
		 */
		System.err.println("EMAIL START " + emailStart.getDate() + " " + emailStart.getTopicKey() + " " + emailStart.getEmailAddress() );
	}

	public void upsertEmailMeta(String path, EmailMeta emailMeta) {
		// stashes it in a hashtable by path
		// one hashtable per topic
		TopicDay topicDay = new TopicDay(emailMeta.getTopicKey(), emailMeta.getDay(), path);
		feedDay(topicDay);
	}

	public List<EmailOut> whatComingDays(EmailStart emailStart, LocalDate thisDate) {
		return dayRange(emailStart, false, thisDate);
	}

	public List<EmailOut> todaysEmail(EmailStart emailStart, LocalDate thisDate) {
		return dayRange(emailStart, true, thisDate);
	}

	public Map<String, EmailStart> getEmailStarts() {
		return emailStartMap;
	}

	private List<EmailOut> dayRange(EmailStart emailStart, boolean isTodayNotFuture, LocalDate thisDate) {
		int days = elapsedDays(emailStart, thisDate);
		Map<String, TopicDay> topicDayMap = fetchTopicDayMap(emailStart.getTopicKey());
		List<EmailOut> emails = new ArrayList<EmailOut>();
		for (TopicDay topicDay : topicDayMap.values()) {
			if ((isTodayNotFuture && isToday(days, topicDay))
					|| (!isTodayNotFuture && isComingDay(days, topicDay))) {
				addEmailsToEmailOut(emails, emailStart, topicDay, days, thisDate);
			}
		}
		return emails;
	}

	private void addEmailsToEmailOut(List<EmailOut> emails, EmailStart emailStart, TopicDay topicDay, int days, LocalDate thisDate) {
		emails.add(new EmailOut(emailStart.getEmailAddress(), topicDay.getEmailKey(), emailStart.getTopicKey(),
				null, dateFuture(topicDay.getDay() - days, thisDate)));
	}

	private boolean isComingDay(int days, TopicDay topicDay) {
		return (days - topicDay.getDay() < 0);
	}

	private boolean isToday(int days, TopicDay topicDay) {
		return (days - topicDay.getDay() == 0);
	}

	public void delete(TopicDay topicDay) {
		Map<String, TopicDay> topicDayMap = fetchTopicDayMap(topicDay.getTopicKey());
		topicDayMap.remove(topicDay);
	}

	/*
	 * dateToday externalized as argument for testing purposes only
	 */
	String dateFuture(int days, LocalDate localDate) {
		LocalDate futureDate = localDate.plusDays(days);
		return futureDate.format(DateTimeFormatter.ofPattern("yyMMdd"));
	}

	/*
	 * dateToday externalized as argument for testing purposes only
	 */
	int elapsedDays(EmailStart emailStart, LocalDate thisDate) {
		String zeroDay = emailStart.getDate();
		LocalDate zeroDate = LocalDate.parse(zeroDay, DateTimeFormatter.ofPattern("yyMMdd"));
		long diffInDays = ChronoUnit.DAYS.between(zeroDate, thisDate);
		return (int) diffInDays;
	}

	void feedDay(TopicDay topicDay) {
		String key = topicDay.getEmailKey().substring(20, topicDay.getEmailKey().length());
		Map<String, TopicDay> topicDayMap = fetchTopicDayMap(topicDay.getTopicKey());
		topicDayMap.put(key, topicDay);
		topicDaysMap.put(topicDay.getTopicKey(), topicDayMap);
		System.err.println("TOPIC DAY " +topicDay.getTopicKey() + " "+ topicDayMap.size() + " "+ topicDay.getDay() + " " +topicDay.getEmailKey() );
	}

	Map<String, TopicDay> fetchTopicDayMap(String topicKey) {
		Map<String, TopicDay> topicDayMap = new Hashtable<String, TopicDay>();
		if (topicDaysMap.containsKey(topicKey)) {
			topicDayMap = topicDaysMap.get(topicKey);
		} else {
			topicDayMap = new Hashtable<String, TopicDay>();
			topicDaysMap.put(topicKey, topicDayMap);
		}
		return topicDayMap;
	}

	/*
	 * Save this sysout for confidence building measure when you get lost and doubt
	 * that it works Since testing is so far, a shit show
	 * 
	 */
	public void printTopicDaysMap(){
		System.err.println("TOPIC KEYS HAS " + topicDaysMap.size());
		for(String value: topicDaysMap.keySet()) {
			System.err.println("TOPIC KEY " + value);
		}
		
	}

}
