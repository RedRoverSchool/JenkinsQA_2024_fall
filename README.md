# JenkinsQA_2024_fall

<h1 align="center">Project for Testing the CI/CD Tool Jenkins</h1>
<p align="center">
  <img src="media/jenkins.svg" alt="Jenkins logo" width="100"/>
</p>

> Jenkins is an automation tool designed for Continuous Integration (CI) and Continuous Deployment (CD). It allows automatic execution of build, testing, and deployment processes whenever code changes are made in the repository.

##  Content:

- <a href="#cases"> Test documentation</a>
- <a href="#settings"> Settings</a>
- <a href="#autotests"> Running Tests Locally</a>
- <a href="#allureReport"> Allure Report Example</a>
- <a href="#tg"> Telegram Bot Notification</a>

## Tech Stack

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/TestNG-FF8C00?style=for-the-badge&logo=testng&logoColor=white"/>
  <img src="https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white"/>
  <img src="https://img.shields.io/badge/RestAssured-6DB33F?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/WireMock-FF4154?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/HTTP-005571?style=for-the-badge&logo=http&logoColor=white"/>
  <img src="https://img.shields.io/badge/Cucumber-23D96C?style=for-the-badge&logo=cucumber&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/Allure-EB5A5A?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
</p>

____
<a id="cases"></a>
## ️ Test documentation

- [📊 Feature Matrix](https://docs.google.com/spreadsheets/d/11v5GXk0FmRQh8te88jC_ygIHz88kpFgUvTIgDxOT1ZY/edit?gid=1912652394)
- [📋 Project Board](https://github.com/orgs/RedRoverSchool/projects/3)

____
<a id="settings"></a>
## ️ Settings

To configure the project locally, follow these steps:

1. **Run Jenkins locally**
    - Install and start Jenkins on your machine.
    - Ensure all required plugins are installed.

2. **Configure the settings file**
    - Copy `local.properties.TEMPLATE`.
    - Rename it to `local.properties` (remove `TEMPLATE`).
    - Fill in the necessary values with your local configuration.

After completing these steps, your project should be ready to use! 🚀

____
<a id="autotests"></a>
## Running Tests Locally

To run automated tests locally, use the following commands:

1. **Run all tests:**
    - ```mvn test```

2. **Run specific tests:**
    - ```mvn test -Dtest=TestName```

3. **Run a specific test suite:**
    - ```mvn clean test -Dsurefire.suiteXmlFiles=suite/<suite-name>```
      Replace <suite-name> with the actual suite file name.

4. **Run tests in a Docker Container:**
    - Using Dockerfile
        - **Build the Docker image:**
          `docker build -t img-fall-2024 .`
          `-t img-fall-2024` – assigns a name to the image.
          `.` (dot) – specifies that the image is built from the current directory.
        - **Run the container:**
          `docker run --rm --name my-container img-fall-2024`
          `--rm` – removes the container after execution.
          `--name my-container` – assigns a name to the container.
          `img-fall-2024` – specifies the image to use for the container.
    - Using Docker Compose
        - **Start the container:**
          `docker-compose up `
          `-d` – runs the containers in the background.
        - **Stop and remove the container:**
          `docker-compose down`
          This method automatically handles image mounting and container execution. 🚀

____
<a id="allureReport"></a>
## <img width="30" style="vertical-align:middle" title="Allure Report" src="media/allure.svg"> Allure Report Example

Example of the [Allure Report](https://redroverschool.github.io/JenkinsQA_2024_fall/2812/)

<p align="center">
  <img title="Allure Report" src="media/allure-report.png">
</p>

____
<a id="tg"></a>
## <img width="30" style="vertical-align:middle" title="Telegram" src="media/telegram.svg"> Telegram Bot Notification

After the build is completed, a bot created in <code>Telegram</code> automatically processes the results and sends a message with the test run report to a [specially configured chat](https://t.me/team_jenkins_2024_fall).

<div style="background-color: #18222d">
<p align="center">
<img width="40%" title="Telegram Notifications" src="media/tg-report.png">
</p>
</div>