FROM java:8-jdk
MAINTAINER jammazwan <pete@datafundamentals.com> "https://github.com/jammazwan"
ENV JAR_LOCATION .
ENV JAR_NAME springbootbarebonescamel.jar
CMD [ "java", "-jar", "springbootbarebonescamel.jar" ]