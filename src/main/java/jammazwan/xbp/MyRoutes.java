package jammazwan.xbp;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.ApplicationContext;

public class MyRoutes extends RouteBuilder {

	ApplicationContext ctx;

	public MyRoutes(ApplicationContext ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public void configure() throws Exception {
		from("timer://barebones?fixedRate=true&period=1000").bean((MyBean) ctx.getBean("myBean"))
				.log("\n\n\t Control C to exit");
	}
}