package cdsf;

import java.util.Arrays;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import cdsf.service.FirebaseDbListeners;
import cdsf.service.WriteCampaignToProps;
import cdsf.util.AppCtxt;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner go(ApplicationContext ctx) throws Exception {
		return (args) -> {
			org.apache.camel.main.Main main = new org.apache.camel.main.Main();
			main.addRouteBuilder(new MyRoutes());
			/*
			 * Since autowired is not working, attempting to figure out when beans are written
			 */
	        String[] beanNames = ctx.getBeanDefinitionNames();
	        Arrays.sort(beanNames);
	        for (String beanName : beanNames) {
	            System.out.println(beanName);
	        }
			AppCtxt.getInstance().setContext(ctx);
			//Moved these guys to FirebaseDbListeners to see if it would work
//			UsersRepository ur = new UsersRepository();
//			new DbCodeReference().go();
//			new InitializeCampaignType().go();
//			new InitializeProductCategory().go();
			main.run();

		};
	}
	
	/*
	 * Seems to work, yet can't pick it up via autowired
	 */
	@Bean
    public WriteCampaignToProps writeCampaignToProps() {
		WriteCampaignToProps writeCampaignToProps = new WriteCampaignToProps();
        return writeCampaignToProps;
    }

	private class MyRoutes extends RouteBuilder {
		@Override
		public void configure() throws Exception {
			from("timer://dblistener?repeatCount=1").log("\n\n\t RAN THIS ONE TIME ONLY").bean(new FirebaseDbListeners()).log("\n\n\t IS FIREBASE STILL RUNNING?");
			from("timer://barebones?fixedRate=true&period=10000").log("\n\n\t Control C to exit");
		}
	}
}