### SpringBootBareBonesCamelMicroService NOTES:

Please note that there are some gymnastics shown here that may seem a bit un-necessary compared to the hello world level documentation found in the standard spring boot examples.

 * Separate MyConfig instead of putting this in the ...Application class
 * Separate MyRoutes instead of putting this in the ...Application class
 * MyRoutes is configured with an ApplicationConfiguration arg in the constructor
 * MyRoutes does NOT use an @Autowired MyBean, but instead uses a clumsier  ctx.getBean("myBean") inside the route definition
 
 WTF? Why all the gymnastics when a simpler approach should work?
 
 #### History of this example:
 
 For over a year, I had this example running, and was using it in my own projects, but was having all heck getting anything with @Autowired to work.
 
 To make a very long story a bit shorter, the camel route was running before the application context had initialized. So I was getting a null pointer on any autowiring. A bit of a chicken and egg problem.
 
 The app in it's current state allows spring context to finish initializing first, and only then does it call on applicationContext.getBean("anyBeanIDefinedPreviously").
 
 If you find an easier or more canonical way to do this, please feel free to let me know at pete at datafundamentals dot com.
 