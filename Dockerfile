# 第一阶段：使用 Maven 打包
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

# 第二阶段：运行打包好的 jar 包
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# 动态读取 Render 分配的端口，默认 8080
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:8080}"]
