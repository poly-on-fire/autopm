package pull.camel;

import java.io.InputStream;
import java.util.Scanner;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecResult;
import org.springframework.context.ApplicationContext;

import pull.service.DeployCheck;

public class TimedWebDeployRoutes extends RouteBuilder {
	DeployCheck deployCheck;

	public TimedWebDeployRoutes(ApplicationContext applicationContext) {
		super();
		this.deployCheck = (DeployCheck) applicationContext.getBean("deployCheck");
	}

	@Override
	public void configure() throws Exception {
		from("timer://deployPages?period=1m&delay=20000").process(new DeploymentProcessor(deployCheck));

		from("direct:deploy").to("exec:./deploy.sh?workingDir=../infoclouddancer/&timeout=100000")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						/* I don't think this is the easy way but too bad */
						ExecResult result = (ExecResult) exchange.getIn().getBody();
						InputStream fis = result.getStderr();
						if (fis != null) {
							String inputStreamString = new Scanner(fis, "UTF-8").useDelimiter("\\A").next();
							System.err.println("ERROR " + inputStreamString);
						}
					}
				}).log("DEPLOYED");
	}
}
