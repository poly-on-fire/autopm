package pull.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pull.domain.Topic;

@Service
public class WriteTopicToProps {
	@Autowired
	private DeployCheck deployCheck;
	
	public WriteTopicToProps() {
		super();
	}

	public synchronized void go(String key, Topic topic) {

		boolean hasNecessaryStuff = false;
		boolean isGeneratingType = false;
		if (isField(topic.getAHeadline()) && isField(topic.getAPost())
				&& isField(topic.getAPre()) && isField(topic.getAVideo())
				&& isField(topic.getBHeadline()) && isField(topic.getBPre())
				&& isField(topic.getBPost()) && isField(topic.getBVideo())) {
			hasNecessaryStuff = true;
		}
		String type = topic.getTopicType();
		System.out.println("LOOK HERE FOR CHANGING DEPLOYMENT TO OPTIONAL\n"+type);
//		if (type.contains("OTG") || type.contains("Kuchel") || type.contains("London")) {
			isGeneratingType = true;
//		}
		if (isGeneratingType && hasNecessaryStuff) {
			writePropertiesFile(key, topic);
		} else if (isGeneratingType && !hasNecessaryStuff) {
			printProblemTopic(topic);
		}
	}

	private void writePropertiesFile(String key, Topic topic) {
		Properties properties = parseTopicIntoProperties(topic);
		writeFile(properties, key);
		setDeploymentFlag();
	}
	
	private void setDeploymentFlag(){
		deployCheck.setDeploymentFlag(true);
	}

	void writeFile(Properties properties, String key) {
		try {
			File file = new File("propertyFiles/" + key + ".properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, key + " topic");
			fileOut.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	Properties parseTopicIntoProperties(Topic topic) {
		Properties properties = new Properties();
		properties.put("topicType", topic.getTopicType());
		properties.put("name", topic.getName());
		properties.put("timeStamp", "" + topic.getTimestamp());
		properties.put("aHeadline", clean(topic.getAHeadline()));
		properties.put("aPre", clean(topic.getAPre()));
		properties.put("aVideo", clean(topic.getAVideo()));
		properties.put("aPost", clean(topic.getAPost()));
		properties.put("bHeadline", clean(topic.getBHeadline()));
		properties.put("bPre", clean(topic.getBPre()));
		properties.put("bVideo", clean(topic.getBVideo()));
		properties.put("bPost", clean(topic.getBPost()));
		return properties;
	}

	String clean(String string) {
		string = string.replaceAll(Pattern.quote("${"), "-[");
		string = string.replaceAll(Pattern.quote("{"), "[");
		string = string.replaceAll(Pattern.quote("}"), "]");
		/* replace /n with <br> */
		string = string.replaceAll("%20%0A", "%3Cbr%3E");
		/*
		 * see http://stackoverflow.com/questions/6666423/overcoming-display-
		 * forbidden-by-x-frame-options
		 */
		string = string.replaceAll("watch%3Fv%3D", "embed/");
		string = string.replaceAll("youtu.be%2F", "youtube.com/embed/");
		return string;
	}

	private boolean isField(String string) {
		if (string != null && string.length() > 1) {
			return true;
		} else {
			return false;
		}
	}

	private void printProblemTopic(Topic topic) {
		System.err.println("LOOK HERE FOR SOMETHING NULL THAT SHOULD NOT BE");
		System.err.println("1" + topic.getAHeadline());
		System.err.println("2" + topic.getAPre());
		System.err.println("3" + topic.getAVideo());
		System.err.println("4" + topic.getAPost());
		System.err.println("5" + topic.getBHeadline());
		System.err.println("6" + topic.getBPre());
		System.err.println("7" + topic.getBVideo());
		System.err.println("8" + topic.getBPost());
	}
}
