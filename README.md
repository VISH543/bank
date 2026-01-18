# Bank Application

This repository contains a multi-module banking application and a consumer service.

## Project Structure

- **bank-app-parent**: The main banking application (multi-module).
- **Consumer-service**: A separate consumer service application.
- **deployment**: Contains deployment scripts and configuration guides.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Git

### Installation

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/VISH543/bank.git
    cd bank
    ```

2.  **Build the projects**:
    You need to build both the Bank App and the Consumer Service.

    ```bash
    # Build Bank App
    cd bank-app-parent
    mvn clean install

    # Build Consumer Service
    cd ../Consumer-service
    mvn clean install
    ```

### Running the Application

This application is designed to be deployed on Apache Tomcat (or compatible server).

**Please refer to [deployment/DEPLOYMENT.md](deployment/DEPLOYMENT.md) for detailed instructions on how to configure Tomcat and deploy the EAR files.**

## Quick Deployment (Script)

If you have Tomcat installed, you can use the provided script:

```bash
cd deployment/scripts
export TOMCAT_HOME=/path/to/your/tomcat
./deploy.sh
```
