package pull.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
//TODO I don't think this class is even being used? Move to cruft and then out?
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
