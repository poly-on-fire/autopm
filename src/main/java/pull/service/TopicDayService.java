package pull.service;

import org.springframework.stereotype.Service;
import pull.domain.EmailMeta;
import pull.domain.EmailOut;
import pull.domain.EmailStart;
import pull.domain.TopicDay;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TopicDayService {
    /*
     * How does this class work? By creating in memory maps of the main two raw ingredients in the background,
     * and then providing a single access point for emails that go out today, per emailStart.
     *
     * This class says to the rest of the app: "Hey while you were churning away doing your thing,
     * I was building an in memory version of two different maps that I could use to instantly grind out an email list."
     *
     * The two in memory maps are
     *
     * 1. When a day's email is added to a topic, EmailMetaRepo calls this and topicDaysMap is increased by that one.
     *
     * 2. When an EmailStart is added, EmailStartRepo calls this and emailStartMap is increased by one
     *
     * THEN: WHEN IT"S TIME TO SEND EMAILS:
     * When DailyEmailRoutes runs it calls DailyEmail.send() which loops through the emailStartMap referenced above,
     * and sends out whatever email is appropriate for this day.
     * To do this, it calls the todaysEmail() method with any emailStart, and today's date
     *
     * There is also something modestly confusing in the stuff below, called the emailKey
     * This is a really confusing name, but really what it is the path to emailMeta.
     * Looks like this:
     * /users/wdjLhZgkhuRZzzWB9khVIexF51L2/emailMeta/-LBSOptlX7dL82H8VRdQ_003
     * The reason we handle this guy is that EmailOut needs it to look up emailContent by this path, slightly modified
     * search for this to understand more 'path = path.replaceAll("emailMeta", "emailContent");'
     */
    public TopicDayService() {
        super();
    }
    // outer map by topicKey, inner map by day
    Map<String, Map<String, TopicDay>> topicDaysMap = new HashMap<String, Map<String, TopicDay>>();
    private Map<String, EmailStart> emailStartMap = new HashMap<String, EmailStart>();


    /*
    See #1 above called from repo at setup and any add - still uses path
     */
    public void upsertEmailMeta(String path, EmailMeta emailMeta) {
        TopicDay topicDay = new TopicDay(emailMeta.getTopicKey(), emailMeta.getDay(), path);
        feedDay(topicDay);
    }

    /*  See #2 above called from repo at setup and any add  */
    public void upsertEmailStart(EmailStart emailStart) {
        String key = emailStart.getTopicKey() + emailStart.getEmailAddress();
        emailStartMap.put(key, emailStart);
        /*
         * Save this sysout for confidence building measure when you get lost and doubt
         * that it works Since testing is so far, a shit show
         */
//        System.err.println("EMAIL START " + emailStart.getDate() + " " + emailStart.getTopicKey() + " " + emailStart.getEmailAddress());
    }

    public List<EmailOut> todaysEmail(EmailStart emailStart, LocalDate thisDate) {
        return dayRange(emailStart, true, thisDate);
    }

    public List<EmailOut> whatComingDays(EmailStart emailStart, LocalDate thisDate) {
        return dayRange(emailStart, false, thisDate);
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
        Map<String, TopicDay> topicDayMap = fetchTopicDayMap(topicDay.getTopicKey());
        topicDayMap.put(""+topicDay.getDay(), topicDay);
        topicDaysMap.put(topicDay.getTopicKey(), topicDayMap);
//        System.err.println("TOPIC DAY "  + topicDay.getTopicKey() + " " + topicDayMap.size() + " " + topicDay.getDay() + " " + topicDay.getEmailKey());
//        printTopicDaysMap();
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
    public void printTopicDaysMap() {
        System.err.println("\tTOPIC KEYS HAS " + topicDaysMap.size());
        for (String value : topicDaysMap.keySet()) {
            System.err.println("\t\tTOPIC KEY " + value);
        }

    }

}
