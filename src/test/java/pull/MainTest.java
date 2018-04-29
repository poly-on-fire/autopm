package pull;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import pull.Main;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(AppConfig.class)
public class MainTest {
	// see https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
	// I never got the test to run, kind of ran out of patience before resolving anything
	// for example you might try TestConfiguration

	@Test
	public void contextLoads() {
		System.out.println("THIS NEVER SEEMS TO RUN");
	}

}
