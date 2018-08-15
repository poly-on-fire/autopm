package pull.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pull.cruft.FetchSendEmailRepo;
import pull.domain.EmailOut;
import pull.domain.EmailStart;
import pull.util.SendEmail;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class DailyEmail {
    @Autowired
    private TopicDayService topicDayService;
    @Autowired
    SendEmail sendEmail;
    @Value("${EMAIL_FROM}")
    private String emailFrom;

    /*
    If you search for who calls this it seems like nobody!but test! This is not correct.
    DailyEmailRoutes calls this, but only because it is the only method in this class.
    Specifically .bean(dailyEmail) is what calls the only method, which is send().
     */

    public void send() {
        System.out.println("SOMETHING SHOULD HAVE HAPPENED to at least "+ topicDayService.getEmailStarts().values().size());

        /*
         * This should seem confusing because it is getting a list of emails for
         * one day for one topic for one email address. Although this is
         * NEVER OK to have more than one email per topic per email address,
         * it is still physically possible, so that is what has to be done.
         */
        topicDayService.printTopicDaysMap();
        /* so this next line gets a de-duped list, that's about it*/
        Collection<EmailStart> emailStarts = (Collection<EmailStart>) topicDayService.getEmailStarts().values();
        for (EmailStart emailStart : emailStarts) {
            System.out.println("\tDE START  "+ emailStart.getEmailAddress());
            List<EmailOut> emailsOut = topicDayService.todaysEmail(emailStart, LocalDate.now());
            System.out.println("\tDE OUTS  "+ emailsOut);
            for (EmailOut emailOut : emailsOut) {
                new FetchSendEmailRepo(emailFrom, emailOut, emailStart, sendEmail);
                System.out.println("\tDE emailOut emailStart.emailAddress  "+ emailOut + " " + emailOut.getEmailAddress());
            }

        }
    }

}
