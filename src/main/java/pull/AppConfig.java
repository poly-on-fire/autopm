package pull;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pull.camel.PullRoutes;
import pull.camel.DailyEmailRoutes;
import pull.camel.DevSeedRoutes;
import pull.camel.TimedWebDeployRoutes;
import pull.repo.EmailLogRepo;
import pull.repo.EmailStartRepo;
import pull.repo.EmailVerifyUnsubscribeRepo;
import pull.repo.NewUserRepo;
import pull.repo.ServiceActionRepo;
import pull.repo.UsersRepo;
import pull.service.MessageUtils;
import pull.service.TopicDayService;
import pull.service.DailyEmail;
import pull.service.DeployCheck;
import pull.service.DevSeed;
import pull.service.EmailQueue;
import pull.service.NewServiceRequest;
import pull.service.SendEmailVerifyEmail;
import pull.service.WriteTopicToProps;
import pull.util.SendEmail;

@Configuration
public class AppConfig implements ApplicationContextAware {
	private boolean runNaked;
	private boolean devSeed;

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		runNaked = Boolean.valueOf(applicationContext.getEnvironment().getProperty("run.naked"));
		devSeed = Boolean.valueOf(applicationContext.getEnvironment().getProperty("dev.seed"));
	}

	@Bean
	public TopicDayService topicDayService() {
		return new TopicDayService();
	}

	@Bean
	public EmailStartRepo emailStartRepo() {
		return new EmailStartRepo();
	}

	@Bean
	public EmailLogRepo emailLogRepo() {
		return new EmailLogRepo();
	}

	@Bean
	public EmailVerifyUnsubscribeRepo emailVerifyUnsubscribeRepo() {
		return new EmailVerifyUnsubscribeRepo();
	}

	@Bean
	public ServiceActionRepo serviceActionRepo() {
		return new ServiceActionRepo();
	}

	@Bean
	public MessageUtils autosponderUtils() {
		return new MessageUtils();
	}

	@Bean
	public NewUserRepo newUserRepo() {
		return new NewUserRepo();
	}

	@Bean
	public NewServiceRequest newServiceRequest() {
		return new NewServiceRequest();
	}

	@Bean
	public DeployCheck deployCheck() {
		return new DeployCheck();
	}

	@Bean
	public DailyEmail dailyEmail() {
		return new DailyEmail();
	}

	@Bean
	public EmailQueue mailQueue() {
		return new EmailQueue();
	}

	@Bean
	public SendEmail sendEmail() {
		return new SendEmail();
	}

	@Bean
	public SendEmailVerifyEmail sendEmailVerifyEmail() {
		return new SendEmailVerifyEmail();
	}

	@Bean
	public UsersRepo usersRepo() {
		return new UsersRepo(applicationContext);
	}

	@Bean
	public DevSeed devSeed() {
		return new DevSeed(applicationContext);
	}

	@Bean
	public CommandLineRunner go(ApplicationContext ctx) throws Exception {
		return (args) -> {
			org.apache.camel.main.Main main = new org.apache.camel.main.Main();
			if (!runNaked) {
				main.addRouteBuilder(new PullRoutes(ctx));
				main.addRouteBuilder(new TimedWebDeployRoutes(ctx));
				main.addRouteBuilder(new DailyEmailRoutes(ctx));
			}
			/*
			 * Attempting to figure out when beans are written
			 */
			if(devSeed) {
				main.addRouteBuilder(new DevSeedRoutes(ctx));
			}
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println("BEAN " + beanName);
			}
			main.run();

		};
	}

	/*
	 * Seems to work, yet can't pick it up via autowired
	 */
	@Bean
	public WriteTopicToProps writeTopicToProps() {
		WriteTopicToProps writeTopicToProps = new WriteTopicToProps();
		return writeTopicToProps;
	}

}
