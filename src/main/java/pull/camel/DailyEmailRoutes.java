package pull.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.ApplicationContext;

import pull.service.DailyEmail;

public class DailyEmailRoutes extends RouteBuilder {
	/*
	 * These times are based on a crude/fast study done of best times to send
	 * and email, and then adjusted to make sure it is a different time every
	 * day. Not intended to be perfect, just good enough.
	 */
	ApplicationContext applicationContext;
	DailyEmail dailyEmail;
	
	public DailyEmailRoutes(ApplicationContext applicationContext){
		super();
		this.applicationContext = applicationContext;
		this.dailyEmail = (DailyEmail)applicationContext.getBean("dailyEmail");
	}
	@Override
	public void configure() throws Exception {
//		from("quartz2://dailyEmail/myTimerName?fireNow").bean(dailyEmail);
//		from("quartz2://dailyEmail/myTimerName?cron=0+0/1+*+?+*+*").bean(dailyEmail);
//		from("quartz2://dailyEmail/friday?cron=0+18+6+?+*+FRI").bean(dailyEmail);
		
		from("quartz2://dailyEmail/monday?cron=0+15+12+?+*+MON").bean(dailyEmail);
		from("quartz2://dailyEmail/tuesday?cron=0+5+11+?+*+TUE").bean(dailyEmail);
		from("quartz2://dailyEmail/wednesday?cron=0+45+9+?+*+WED").bean(dailyEmail);
		from("quartz2://dailyEmail/thursday?cron=0+10+10+?+*+THU").bean(dailyEmail);
		from("quartz2://dailyEmail/friday?cron=0+15+11+?+*+FRI").bean(dailyEmail);
		from("quartz2://dailyEmail/saturday?cron=0+35+7+?+*+SAT").bean(dailyEmail);
		from("quartz2://dailyEmail/sunday?cron=0+45+14+?+*+SUN").bean(dailyEmail);
	}

}
