package pull.camel;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

//	TODO: Comment to the effect that this is just a helper class for velocity templates
public class VelocityHeaderPropertiesProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);

		for (String line : body.split("\n")) {
			if (!line.startsWith("#") && line.contains("=")) {
				addToHeaders(line, exchange);
			}
		}
	}

	private void addToHeaders(String line, Exchange exchange) {
		String key = line.substring(0, line.indexOf("=")).trim();
		String value = line.substring(line.indexOf("=") + 1, line.length()).trim();
		try {
			value = URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		exchange.getIn().setHeader(key, value);
	}

}
