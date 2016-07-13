package jammazwan.xbp;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BarebonesspringbootcamelApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarebonesspringbootcamelApplication.class, args);
	}

	@Bean
	public CommandLineRunner go() throws Exception {
		return (args) -> {
			Main main = new Main();
			main.addRouteBuilder(new MyRoutes());
			main.run();
		};
	}

	private class MyRoutes extends RouteBuilder {
		@Override
		public void configure() throws Exception {
			from("timer://barebones?fixedRate=true&period=1000").log("\n\n\t Control C to exit");
		}
	}
}