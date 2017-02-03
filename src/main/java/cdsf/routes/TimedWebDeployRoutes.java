package cdsf.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecBinding;
//import org.apache.camel.component.exec.ExecResult;

public class TimedWebDeployRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// from("timer://runEveryMinute?fixedRate=1000").log("runEverySecond");
		from("timer://whasit?period=1m").to("direct:checkGitHead");

		from("direct:checkGitHead").process(new Processor() {
			// processor setting headers see camel-exec docs for more
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().setHeader(ExecBinding.EXEC_COMMAND_TIMEOUT, 5000);
				exchange.getIn().setHeader(ExecBinding.EXEC_USE_STDERR_ON_EMPTY_STDOUT, true);
			}
		}).to("exec:./../deployclouddancer/gitpull.sh").process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				String result = exchange.getIn().getBody(String.class);
				if (result.startsWith("Already")) {
					exchange.getIn().setHeader("deployToServer", false);
				} else if (result.contains("change") || result.contains("insertion") || result.contains("Merge")) {
					exchange.getIn().setHeader("deployToServer", true);
					log.info("setting camel header for DEPLOYING a latest release to server");
				} else {
					exchange.getIn().setHeader("deployToServer", false);
					String error = "GIT PULL RESULT WAS NOT ANTICPATED SEE TimedWebDeployRoutes.java ........\n"
							+ result + "\n\n";
					System.err.println(error);
					log.error(error);
					throw new RuntimeException(error);
				}
			}
		}).choice().when(header("deployToServer").isEqualTo(true)).to("direct:buildclouddancer")
				.when(header("deployToServer").isEqualTo(false)).to("direct:nodeploy");

		from("direct:nodeploy").log("WOULD NOT HAVE BEEN DEPLOYED");

		from("direct:buildclouddancer").log("WOULDA BEEN BUILT HERE").process(new Processor() {
			// processor setting headers see camel-exec docs for more
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().setHeader(ExecBinding.EXEC_COMMAND_TIMEOUT, 5000);
				exchange.getIn().setHeader(ExecBinding.EXEC_COMMAND_ARGS, "build");
				exchange.getIn().setHeader(ExecBinding.EXEC_USE_STDERR_ON_EMPTY_STDOUT, true);
			}
		}).to("exec:./polymerBuild.sh?workingDir=../deployclouddancer/").process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				String result = exchange.getIn().getBody(String.class);
				if (result.contains("Ignoring duplicate element definition:")) {
					exchange.getIn().setHeader("deployToServer", true);
				} else {
					exchange.getIn().setHeader("deployToServer", false);
					String error = "GIT PULL RESULT WAS NOT ANTICPATED SEE TimedWebDeployRoutes.java ........\n"
							+ result + "\n\n";
					System.err.println(error);
					log.error(error);
					throw new RuntimeException(error);
				}
			}
		}).choice().when(header("deployToServer").isEqualTo(true)).to("direct:deployclouddancer")
				.when(header("deployToServer").isEqualTo(false)).to("direct:nodeploy");
		;

		from("direct:deployclouddancer").log("CLOUDDANCER WOULDA BEEN SCP'd HERE");
		
//		.process(new Processor() {
//			// processor setting headers see camel-exec docs for more
//			public void process(Exchange exchange) throws Exception {
//				exchange.getIn().setHeader(ExecBinding.EXEC_COMMAND_TIMEOUT, 5000);
//				exchange.getIn().setHeader(ExecBinding.EXEC_COMMAND_ARGS, "build");
//				exchange.getIn().setHeader(ExecBinding.EXEC_USE_STDERR_ON_EMPTY_STDOUT, true);
//			}
//		}).to("exec:../deployclouddancer/polymer").process(new Processor() {
//			public void process(Exchange exchange) throws Exception {
//				String result = exchange.getIn().getBody(String.class);
//				if (result.contains("Ignoring duplicate element definition:")) {
//					exchange.getIn().setHeader("deployToServer", false);
//				} else {
//					String error = "GIT PULL RESULT WAS NOT ANTICPATED SEE TimedWebDeployRoutes.java ........\n"
//							+ result + "\n\n";
//					System.err.println(error);
//					log.error(error);
//					throw new RuntimeException(error);
//				}
//			}
//		}).choice().when(header("deployToServer").isEqualTo(true)).to("direct:deployclouddancer")
//				.when(header("deployToServer").isEqualTo(false)).to("direct:nodeploy");
//		;

		// }).choice().when(header("deployToServer").isEqualTo(true)).to("direct:deployclouddancer").when(header("deployToServer").isEqualTo(false)).to("direct:deployclouddancer").log("hi");
		// #1

		//
		// from("direct:xbr").process(new Processor() {
		// public void process(Exchange exchange) throws Exception {
		// String text = exchange.getIn().getBody(String.class);
		// String newAnswer = "My " + text;
		// exchange.getOut().setBody(newAnswer);
		// }
		// });
		// from("direct:resolvePropertyPlaceholders").process(new Processor() {
		// public void process(Exchange exchange) throws Exception {
		// String text = exchange.getIn().getBody(String.class);
		// String newAnswer = text + " " +
		// getContext().resolvePropertyPlaceholders("{{gitusername}}");
		// exchange.getOut().setBody(newAnswer);
		// }
		// });
		/*
		 * camel-git would be the preferred approach but does not appear to be
		 * ready for prime time. Got a "USERAUTH fail" for no logical reason,
		 * decided that rather than keep messing with it easier to just use git
		 * from a shell script and a camel-exec command
		 */
		// from("direct:start")
		// .to("git://../../clouddancer?operation=pull&remoteName=origin&username=betterology$password=xxxx398xxxx")
		// .process(new Processor() {
		// public void process(Exchange exchange) throws Exception {
		// Status response = exchange.getIn().getBody(Status.class);
		// String newAnswer = response.isClean() + " "
		// + getContext().resolvePropertyPlaceholders("{{gitusername}}");
		// exchange.getOut().setBody(newAnswer);
		// }
		// });
		// ExecBinding.EXEC_USE_STDERR_ON_EMPTY_STDOUT

	}
}
