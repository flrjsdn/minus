# Base 이미지 설정
FROM openjdk:17-jdk-slim

# Gradle 빌드 후 생성된 JAR 파일을 복사
COPY build/libs/muinus-0.0.1-SNAPSHOT.jar /project/muinus-0.0.1-SNAPSHOT.jar

#EXPOSE 8000

# 애플리케이션 실행 명령
ENTRYPOINT ["java", "-jar", "/project/muinus-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "-Dserver.port=8000", "/project/muinus-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "/project/muinus-0.0.1-SNAPSHOT.jar", "--server.port=8000"]