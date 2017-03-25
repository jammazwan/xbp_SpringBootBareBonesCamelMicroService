package jammazwan.xbp;

import org.apache.camel.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

	
	@Bean
	public CommandLineRunner go(ApplicationContext ctx) throws Exception {
		return (args) -> {
			Main main = new Main();
			main.addRouteBuilder(new MyRoutes(ctx));
			main.run();
		};
	}

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }
}
