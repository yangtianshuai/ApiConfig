FROM openjdk:17-alpine
ADD demo.jar app.jar
#EXPOSE 80 测试服务器端口已经占用
ENTRYPOINT ["java", "-jar", "app.jar"]