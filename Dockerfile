FROM java:8-jdk
MAINTAINER jammazwan <pete@datafundamentals.com> "https://github.com/jammazwan"
WORKDIR /usr/lib
ADD target/springbootbarebonescamel.jar /usr/lib
ENV JAR_LOCATION .
ENV JAR_PORT 65432
ENV JAR_NAME springbootbarebonescamel.jar
CMD [ "java", "-jar", "springbootbarebonescamel.jar" ]
EXPOSE $JAR_PORT