package pull.service;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

import pull.domain.Topic;
import pull.service.WriteTopicToProps;

public class WriteTopicToPropsTest {
	WriteTopicToProps wctp = new WriteTopicToProps();

	@Test
	public void testclean() {
		String string = "My good buddy has ${lots of help} geting implemented";
		String returnValue = new WriteTopicToProps().clean(string);
		assertEquals("My good buddy has -[lots of help] geting implemented", returnValue);
		string = "My good buddy has {more help} geting tested";
		returnValue = new WriteTopicToProps().clean(string);
		assertEquals("My good buddy has [more help] geting tested", returnValue);
		assertEquals("https%3A%2F%2Fwww.youtube.com%2Fembed/JxAXlJEmNMg",
				wctp.clean("https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DJxAXlJEmNMg"));
		assertEquals("https%3A%2F%2Fyoutube.com/embed/7Pq-S557XQU",
				wctp.clean("https%3A%2F%2Fyoutu.be%2F7Pq-S557XQU"));
	}

	@Test
	public void testParseTopicIntoProperties() {
		Topic topic = new Topic("topicType", "name", "category", 1234567890, "aHeadline",
				"aPre", "aVideo", "aPost", "bHeadline", "bPre", "bVideo",
				"bPost");
		Properties properties = new WriteTopicToProps().parseTopicIntoProperties(topic);
		assertEquals(properties.getProperty("aPre"), "aPre");
	}

}
