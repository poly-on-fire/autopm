package pull.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import pull.service.DailyEmail;
import pull.service.DevSeed;

public class DevSeedRoutes extends RouteBuilder {
	ApplicationContext applicationContext;	
	DailyEmail dailyEmail;
	DevSeed devSeed;
	
	public DevSeedRoutes(ApplicationContext applicationContext){
		super();
		this.applicationContext = applicationContext;
		this.dailyEmail = applicationContext.getBean(DailyEmail.class);
		this.devSeed = applicationContext.getBean(DevSeed.class);
	}

	@Override
	public void configure() throws Exception {
		from("timer://something?delay=6s&repeatCount=1").log("\n\n\t .... running  ........ for dev seed").bean(devSeed);
	}
}
