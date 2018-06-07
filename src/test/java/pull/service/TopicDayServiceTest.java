package pull.service;

import org.junit.Test;
import pull.domain.EmailMeta;
import pull.domain.EmailOut;
import pull.domain.EmailStart;
import pull.domain.TopicDay;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TopicDayServiceTest {
    TopicDayService topicDayService = new TopicDayService();
    LocalDate march3rd2017 = LocalDate.parse("2017-03-03");


    @Test
    public void testDateFuture() {
        assertEquals("170318", topicDayService.dateFuture(15, march3rd2017));
        assertEquals("170417", topicDayService.dateFuture(45, march3rd2017));
        assertEquals("170726", topicDayService.dateFuture(145, march3rd2017));
    }

    @Test
    public void testElapsedDays() {
        EmailStart emailStart = new EmailStart(null, null, "170301", null);
        assertEquals(2, topicDayService.elapsedDays(emailStart, march3rd2017));
//		emailStart = new EmailStart(null, null, "170226", null);
//		assertEquals(5, topicDayService.elapsedDays(emailStart));
    }

    /*
     * Verifies that you either get back the right map, or an empty map if none
     * exists
     */
    @Test
    public void testFetchTopicDayMap() {
        String topicKey = "mytopickey";
        String differentKey = "aonthertopickey";
        Map<String, Map<String, TopicDay>> topicDaysMap = new HashMap<String, Map<String, TopicDay>>();
        topicDayService.topicDaysMap = topicDaysMap;
        Map<String, TopicDay> testVariable = topicDayService.fetchTopicDayMap(topicKey);
        assertEquals(0, testVariable.size());
        testVariable.put(topicKey, new TopicDay(topicKey, 1, "emailKey"));
        testVariable = topicDayService.fetchTopicDayMap(topicKey);
        assertEquals(1, testVariable.size());
        testVariable = topicDayService.fetchTopicDayMap(differentKey);
        assertEquals(0, testVariable.size());
        testVariable.put(differentKey, new TopicDay(topicKey, 1, "emailKey"));
        testVariable = topicDayService.fetchTopicDayMap(differentKey);
        assertEquals(1, testVariable.size());
        assertEquals(2, topicDayService.topicDaysMap.size());
    }

    @Test
    public void testFeedDay() {
        String emailMetaPath = "/users/iwanyTWkgtVT4yJrLgFIgjXheBP2/emailMeta/-KcyOJWIpTEZQX7DRsHH";
        String emalPathKey = "yJrLgFIgjXheBP2/emailMeta/-KcyOJWIpTEZQX7DRsHH";
        TopicDay topicDay = new TopicDay("myTopicKey", 17, emailMetaPath);
        topicDayService.feedDay(topicDay);
//        assertEquals(emailMetaPath,
//                topicDayService.topicDaysMap.get("myTopicKey").get(emalPathKey).getEmailKey());
        Map<String, TopicDay> testObject = topicDayService.topicDaysMap.get("myTopicKey");
        assertEquals(Optional.of("17"),testObject.keySet().stream().findFirst() );
        System.out.println();
    }

    @Test
    public void testUpsertEmailMeta() {
        String emailMetaPath = "/users/iwanyTWkgtVT4yJrLgFIgjXheBP2/emailMeta/-L0AoihqAtoQB4dUoZxa";
        EmailMeta emailMeta = new EmailMeta("myTopicKey", null, null, null, 3, null, null, null, null);
        topicDayService.upsertEmailMeta(emailMetaPath, emailMeta);
        assertEquals(1, topicDayService.topicDaysMap.size());
        assertEquals(1, topicDayService.topicDaysMap.get("myTopicKey").size());
        Object obj = topicDayService.topicDaysMap.get("myTopicKey");
        assertTrue ( obj instanceof Hashtable);
    }

    @Test
    public void testWhatComingDays() {
        String emailAddress = "joe@bobs.com";
        String emailKey = "/users/iwanyTWkgtVT4yJrLgFIgjXheBP2/emailMeta/-KcyOJWIpTEZQX7DRsHH";
        String topicKey = "-KcyOJWIpTEZQX7DRsHH";
        String startDate = "170221";
        EmailStart emailStart = new EmailStart(emailAddress, topicKey, startDate, null);
        int i = 0;
        topicDayService.feedDay(new TopicDay(topicKey, 0, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 4, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 7, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 9, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 10, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 11, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 12, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 13, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 16, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 23, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 33, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 45, emailKey + i++));
        List<EmailOut> emailsOut = topicDayService.whatComingDays(emailStart, march3rd2017);
        // 170316, 170309, 170306, 170305, 170304, 170407, 170326
        assertTrue(emailsOut.size() == 7);
        for (EmailOut emailOut : emailsOut) {
            assertTrue("170316, 170309, 170306, 170305, 170304, 170407, 170326".contains(emailOut.getDate()));
        }
    }

    @Test
    public void testTodaysEmail() {
        String emailAddress = "joe@bobs.com";
        String emailKey = "/users/iwanyTWkgtVT4yJrLgFIgjXheBP2/emailMeta/-KcyOJWIpTEZQX7DRsHH";
        String topicKey = "-KcyOJWIpTEZQX7DRsHH";
        String startDate = "170221";
        EmailStart emailStart = new EmailStart(emailAddress, topicKey, startDate, null);
        int i = 0;
        topicDayService.feedDay(new TopicDay(topicKey, 0, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 4, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 7, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 9, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 10, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 11, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 12, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 13, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 16, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 23, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 33, emailKey + i++));
        topicDayService.feedDay(new TopicDay(topicKey, 45, emailKey + i++));
        List<EmailOut> emailsOut = topicDayService.todaysEmail(emailStart, march3rd2017);
        // 170316, 170309, 170306, 170305, 170304, 170407, 170326
        assertTrue(emailsOut.size() == 1);
        for (EmailOut emailOut : emailsOut) {
            assertTrue("170303".contains(emailOut.getDate()));
        }
    }
}
