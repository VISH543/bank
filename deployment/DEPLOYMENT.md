# Tomcat Deployment Guide

## Prerequisites
- Apache Tomcat 10.x (Jakarta EE 9 compatible) or Tomcat 9.x (if using javax.* - check Spring Boot version compatibility. Spring Boot 3 requires Tomcat 10+).
- Java 17+ installed.
- Database (H2, MySQL, or PostgreSQL).

## Configuration Steps

### 1. Database Setup
By default, the application is configured to use H2 file-based database.
- Bank DB: `~/bankdb`
- Consumer DB: `~/consumerdb`
No external setup required for H2. If using MySQL, create databases `bank_db` and `consumer_db`.

### 2. Tomcat Configuration

#### Server.xml
Ensure your `$TOMCAT_HOME/conf/server.xml` has the HTTP connector configured (default port 8080).

#### Context.xml (Datasource)
We have provided `context.xml` templates in `deployment/tomcat/`.
You can copy these content to `$TOMCAT_HOME/conf/context.xml` (global) or specific application context files in `$TOMCAT_HOME/conf/Catalina/localhost/`.

For example:
- Create `$TOMCAT_HOME/conf/Catalina/localhost/bank-api.xml` with content from `deployment/tomcat/bank-api/META-INF/context.xml`.
- Create `$TOMCAT_HOME/conf/Catalina/localhost/consumer.xml` with content from `deployment/tomcat/consumer/META-INF/context.xml`.

**Note:** Since we are deploying EAR files, Tomcat might not process granular `context.xml` the same way as standalone WARs. It is often safer to define Global Resources in `server.xml` and link them in `context.xml`.

### 3. Deployment

Run the automated deployment script:
```bash
cd deployment/scripts
export TOMCAT_HOME=/path/to/your/tomcat
./deploy.sh
```

Or manually:
1. Build both projects using `mvn clean package`.
2. Copy `bank-app-parent/bank-ear/target/bank-ear-*.ear` to `$TOMCAT_HOME/webapps/`.
3. Copy `Consumer-service/consumer-ear/target/consumer-ear-*.ear` to `$TOMCAT_HOME/webapps/`.
4. Start Tomcat: `$TOMCAT_HOME/bin/startup.sh`.

### 4. Verification

Access the applications:
- Bank API: `http://localhost:8080/bank-api`
- Consumer Service: `http://localhost:8080/consumer` (or `http://localhost:8081` if configured on different port/server)

Check logs in `$TOMCAT_HOME/logs/catalina.out`.
