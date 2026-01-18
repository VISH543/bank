#!/bin/bash

# Configuration
TOMCAT_HOME=${TOMCAT_HOME:-/path/to/tomcat}
DEPLOY_DIR=$TOMCAT_HOME/webapps
BANK_APP_DIR=$(pwd)/../bank-app-parent
CONSUMER_APP_DIR=$(pwd)/../Consumer-service

echo "Starting Deployment Process..."

# 1. Build Applications
echo "Building Bank Application..."
cd $BANK_APP_DIR
./mvnw clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "Bank Application build failed!"
    exit 1
fi

echo "Building Consumer Service..."
cd $CONSUMER_APP_DIR
./mvnw clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "Consumer Service build failed!"
    exit 1
fi

# 2. Deploy to Tomcat
echo "Deploying to Tomcat ($DEPLOY_DIR)..."
if [ ! -d "$DEPLOY_DIR" ]; then
    echo "Tomcat directory not found at $DEPLOY_DIR. Please set TOMCAT_HOME environment variable."
    exit 1
fi

# Stop Tomcat (Optional - better to let user manage this or use shutdown.sh if confident)
# $TOMCAT_HOME/bin/shutdown.sh
# sleep 5

# Copy EARs
cp $BANK_APP_DIR/bank-ear/target/*.ear $DEPLOY_DIR/
cp $CONSUMER_APP_DIR/consumer-ear/target/*.ear $DEPLOY_DIR/

# Copy Context Configurations (Optional - if using conf/Catalina/localhost/ or META-INF handling)
# Tomcat usually reads META-INF/context.xml from the WAR/EAR implicitly or needs explicit copy to conf/Catalina/localhost
# For EAR, it's complex. Usually context.xml is per web module.
# The artifacts we created in deployment/tomcat are for reference/manual copy.

echo "Deployment artifacts copied."
echo "Please ensure you have configured context.xml in $TOMCAT_HOME/conf/Catalina/localhost/ if needed,"
echo "or rely on META-INF/context.xml embedded in WARs (if we added them there)."
echo "Restart Tomcat to apply changes."

# Start Tomcat
# $TOMCAT_HOME/bin/startup.sh

echo "Deployment script completed."
