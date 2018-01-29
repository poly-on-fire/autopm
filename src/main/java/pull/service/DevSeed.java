package pull.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class DevSeed {
	ApplicationContext applicationContext;

	public DevSeed(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void go() {
		System.out.println("\n\n\n\n");
		System.out.println("dev seed");
		DailyEmail dailyEmail = applicationContext.getBean(DailyEmail.class);
		dailyEmail.send();
		System.out.println("\n\n\n\n");
	}
}
