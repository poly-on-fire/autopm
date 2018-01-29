package jammazwan.xau;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XauTest extends CamelSpringTestSupport {
	String momFileName = "dearMom.txt";
	String cityFileName = "myCity.json";
	String lsFileName = "lsMe.sh";

	@Override
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
	}

	@Before
	public void cleanMe() {
		deleteFile(momFileName);
	}

	@Test
	public void testDearMom() throws Exception {
		/*
		 * This is designed to show that you can kick off a velocity job just
		 * from the existence of a file. This may or may not be a good idea, but
		 * just one of several ways - see the XauRoutes file. Notice that the
		 * test does nothing other than just ---- test.
		 */
		MockEndpoint mock1 = getMockEndpoint("mock:assert1");
		mock1.expectedFileExists(momFileName);
		mock1.assertIsSatisfied();
	}

	@Test
	public void testCityJson() throws Exception {
		deleteFile(cityFileName);
		Thread.sleep(500);// make sure old file is deleted first
		MockEndpoint mock2 = getMockEndpoint("mock:assert2");
		mock2.expectedFileExists(cityFileName);
		Map<String, Object> replacements = new HashMap<String, Object>();
		replacements.put("rank", "1");
		replacements.put("city", "Austin, TX");
		replacements.put("country", "USA");
		replacements.put("population", "931830");
		replacements.put("sqMiles", "298");
		replacements.put("densitySqMile", "2653");

		// this is where the actual camel work starts:
		template.sendBody("direct:mycity", replacements);

		mock2.assertIsSatisfied();
	}

	@Test
	public void testBashScript() throws Exception {
		deleteFile(lsFileName);
		Thread.sleep(500);// make sure old file is deleted first
		MockEndpoint mock3 = getMockEndpoint("mock:assert3");
		mock3.expectedFileExists(lsFileName);

		// this is where the actual camel work starts:
		template.sendBodyAndHeader("direct:lsme", "No Meaning Here", "myDirectory", "xau_VelocityTemplateToFile");

		mock3.assertIsSatisfied();
	}

	private void deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

}
