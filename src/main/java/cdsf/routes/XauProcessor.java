package cdsf.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class XauProcessor  implements Processor {
	
    public void process(Exchange exchange) throws Exception {
		Object foo = exchange.getIn().getBody();
	}

}
