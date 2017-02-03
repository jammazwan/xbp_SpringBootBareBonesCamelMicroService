package cdsf.routes;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class CdsfRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("file://src/main/resources/?noop=true&fileName=hiMom.txt").process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				Thread.sleep(500);// to allow old file to be deleted
				String text = exchange.getIn().getBody(String.class);
				exchange.getIn().setHeader("hiMessage", text);
			}
		}).to("velocity://velocity/dearMom.txt.vm").to("file:.?fileName=dearMom.txt").to("mock:assert1");

		from("direct:mycity").process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				/*
				 * Here we are installing each of the replacement values as a
				 * header. Velocity knows how to ingest headers. There may be a
				 * better way, but this works.
				 */
				Map<String, Object> replacements = (Map<String, Object>) exchange.getIn().getBody();
				for (String key : replacements.keySet()) {
					exchange.getIn().getHeaders().put(key, replacements.get(key));
				}
			}
		}).to("velocity://velocity/myCity.json.vm").to("file:.?fileName=myCity.json").to("mock:assert2");

		from("direct:lsme").to("velocity://velocity/lsMe.sh.vm").to("file:.?fileName=lsMe.sh").to("mock:assert3");
	}
}
