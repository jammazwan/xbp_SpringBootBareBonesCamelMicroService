package cdsf.util;

import org.springframework.context.ApplicationContext;

public class AppCtxt {
	private static AppCtxt instance;
	private static ApplicationContext applicationContext;
	
	public static AppCtxt getInstance(){
		if(instance==null){
			instance= new AppCtxt();
		}
		return instance;
	}
	
	public void setContext(ApplicationContext ctx){
		applicationContext = ctx;
	}
	
	public  Object get(String name){
		return applicationContext.getBean(name);
	}
	
	public  ApplicationContext getContext(){
		return applicationContext;
	}

}
