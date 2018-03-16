package pull.camel;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

import pull.service.DeployCheck;

public class DeploymentProcessor implements Processor {
	private static int count = 0;

	DeployCheck deployCheck;

	public DeploymentProcessor(DeployCheck deployCheck) {
		this.deployCheck = deployCheck;
	}

	/*
	 * Directed to run every n minutes by timer.
	 * 
	 * if DeployCheck.hasStuffToDeploy() then creates a new template to do just
	 * that
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		if (deployCheck.hasPageToDeploy()) {
			deployCheck.setDeploymentFlag(false);
			ProducerTemplate template = exchange.getContext().createProducerTemplate();
			template.send("direct:deploy", new Processor() {
				@Override
				public void process(Exchange exchange) throws Exception {
					// leaving in here because you might need this once rest of
					// work is more stable
					// exchange.getIn().setHeader(ExecBinding.EXEC_COMMAND_TIMEOUT,
					// 15000);
					// exchange.getIn().setHeader(ExecBinding.EXEC_USE_STDERR_ON_EMPTY_STDOUT,
					// true);
				}
			});
		} else {

			printServer();
		}
	}
	void printServer(){
		if(count<80){
			count++;
			System.err.print(".");
		}else{
			count=0;
			System.err.println(".\n");
		}
	}

}
