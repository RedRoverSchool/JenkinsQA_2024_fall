package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    @Test
    public void testManageJenkinsTab() throws InterruptedException {

        List<WebElement> tasks = getDriver().findElements(
                By.xpath("//div[@id='tasks']//a"));
        Assert.assertEquals(tasks.size(), 4);

        Assert.assertEquals(tasks.get(0).getText(), "New Item");
        Assert.assertEquals(tasks.get(1).getText(), "Build History");
        Assert.assertEquals(tasks.get(2).getText(), "Manage Jenkins");
        Assert.assertEquals(tasks.get(3).getText(), "My Views");

        Thread.sleep(2000);

        WebElement manageJenkinsTask = getDriver().findElement(
                By.xpath("//a[span[text()='Manage Jenkins']]"));
        manageJenkinsTask.click();
    }

    @Test
    public void testManageJenkinsTabSections() throws InterruptedException {

        WebElement manageJenkinsTab = getDriver().findElement(
                By.xpath("//a[span[text()='Manage Jenkins']]"));
        manageJenkinsTab.click();

        Thread.sleep(1000);

        List<WebElement> sections = getDriver().findElements(
                By.xpath("//h2[@class='jenkins-section__title']"));
        Assert.assertEquals(sections.size(), 5);

        Assert.assertEquals(sections.get(0).getText(), "System Configuration");
        Assert.assertEquals(sections.get(1).getText(), "Security");
        Assert.assertEquals(sections.get(2). getText(), "Status Information");
        Assert.assertEquals(sections.get(3).getText(), "Troubleshooting");
        Assert.assertEquals(sections.get(4).getText(), "Tools and Actions");

    }

    @Test
    public void testManageJenkinsTabSectionSystemConfiguration() throws InterruptedException {

        WebElement manageJenkinsTab = getDriver().findElement(
                By.xpath("//a[span[text()='Manage Jenkins']]"));
        manageJenkinsTab.click();

        Thread.sleep(1000);

        WebElement sectionSystemConfiguration = getDriver().findElement(
                By.xpath("//h2[@class='jenkins-section__title' and text()='System Configuration']"));
        Assert.assertEquals(sectionSystemConfiguration.getText(), "System Configuration");

        List<WebElement> systemConfigurationItems = getDriver().findElements(
                By.xpath("//h2[@class='jenkins-section__title' and text()='System Configuration']/following-sibling::div[@class='jenkins-section__items']//div[@class='jenkins-section__item']"));
        Assert.assertEquals(systemConfigurationItems.size(), 6);

        String expectedTextSystem = "System" + "\n" + "Configure global settings and paths.";
        String expectedTextTools = "Tools" + "\n" + "Configure tools, their locations and automatic installers.";
        String expectedTextPlugins = "Plugins" + "\n" + "Add, remove, disable or enable plugins that can extend the functionality of Jenkins.";
        String expectedTextNodes = "Nodes" + "\n" + "Add, remove, control and monitor the various nodes that Jenkins runs jobs on.";
        String expectedTextClouds = "Clouds" + "\n" + "Add, remove, and configure cloud instances to provision agents on-demand.";
        String expectedTextAppearance = "Appearance" + "\n" + "Configure the look and feel of Jenkins";

        Assert.assertEquals(systemConfigurationItems.get(0).getText(), expectedTextSystem);
        Assert.assertEquals(systemConfigurationItems.get(1).getText(), expectedTextTools);
        Assert.assertEquals(systemConfigurationItems.get(2).getText(), expectedTextPlugins);
        Assert.assertEquals(systemConfigurationItems.get(3).getText(), expectedTextNodes);
        Assert.assertEquals(systemConfigurationItems.get(4).getText(), expectedTextClouds);
        Assert.assertEquals(systemConfigurationItems.get(5).getText(), expectedTextAppearance);

//        systemConfigurationItems.get(0).click();
//        Thread.sleep(1000);
//
//        WebElement headerSystem = getDriver().findElement(
//                By.cssSelector("div.jenkins-app-bar__content > h1"));
//        Assert.assertEquals(headerSystem.getText(), "System");
//
//        WebElement buttonManageJenkins = getDriver().findElement(
//                By.xpath("//li[@class='jenkins-breadcrumbs__list-item']/a[@class='model-link' and contains(text(), 'Manage Jenkins')]"));
//        buttonManageJenkins.click();
//        Thread.sleep(2000);
//
//        systemConfigurationItems.get(1).click();
//        Thread.sleep(1000);
//
//        WebElement headerTools = getDriver().findElement(
//                By.xpath("//div[@class='jenkins-app-bar__content']/h1[text()='Tools']"));
//        Assert.assertEquals(headerTools.getText(), "Tools");
//        buttonManageJenkins.click();
//        Thread.sleep(1000);
//
//        systemConfigurationItems.get(2).click();
//        Thread.sleep(1000);
//
//        WebElement headerPlugins = getDriver().findElement(
//                By.xpath("//div[@class='jenkins-app-bar__content']/h1[text()='Plugins']"));
//        Assert.assertEquals(headerPlugins.getText(), "Plugins");
//        buttonManageJenkins.click();
//        Thread.sleep(1000);
//
//        systemConfigurationItems.get(3).click();
//        Thread.sleep(1000);
//
//        WebElement headerNodes = getDriver().findElement(
//                By.xpath("//div[@class='jenkins-app-bar__content']/h1[text()='Nodes']"));
//        Assert.assertEquals(headerNodes.getText(), "Nodes");
//        buttonManageJenkins.click();
//        Thread.sleep(1000);
//
//        systemConfigurationItems.get(4).click();
//        Thread.sleep(1000);
//
//        WebElement headerClouds = getDriver().findElement(
//                By.xpath("//div[@class='jenkins-app-bar__content']/h1[text()='Clouds']"));
//        Assert.assertEquals(headerClouds.getText(), "Clouds");
//        buttonManageJenkins.click();
//        Thread.sleep(1000);
//
//        systemConfigurationItems.get(5).click();
//        Thread.sleep(1000);
//
//        WebElement headerAppearance = getDriver().findElement(
//                By.xpath("//div[@class='jenkins-app-bar__content']/h1[text()='Appearance']"));
//        Assert.assertEquals(headerAppearance.getText(), "Appearance");
//        buttonManageJenkins.click();
//        Thread.sleep(1000);

    }

    @Test
    public void testManageJenkinsTabSectionSecurity() throws InterruptedException {

        WebElement manageJenkinsTab = getDriver().findElement(
                By.xpath("//a[span[text()='Manage Jenkins']]"));
        manageJenkinsTab.click();

        Thread.sleep(1000);

        // Section "Security"
        WebElement sectionSecurity = getDriver().findElement(
                By.xpath("//h2[@class='jenkins-section__title' and text()='Security']"));
        Assert.assertEquals(sectionSecurity.getText(), "Security");

        List<WebElement> securityItems = getDriver().findElements(
                By.xpath("//h2[@class='jenkins-section__title' and text()='Security']/following-sibling::div[@class='jenkins-section__items']//div[@class='jenkins-section__item']"));
        Assert.assertEquals(securityItems.size(), 4);

        String expectedTextSecurity = "Security" + "\n" + "Secure Jenkins; define who is allowed to access/use the system.";
        String expectedTextCredentials = "Credentials" + "\n" + "Configure credentials";
        String expectedTextCredentialProviders = "Credential Providers" + "\n" + "Configure the credential providers and types";
        String expectedTextUsers = "Users" + "\n" + "Create/delete/modify users that can log in to this Jenkins.";

        Assert.assertEquals(securityItems.get(0).getText(), expectedTextSecurity);
        Assert.assertEquals(securityItems.get(1).getText(), expectedTextCredentials);
        Assert.assertEquals(securityItems.get(2).getText(), expectedTextCredentialProviders);
        Assert.assertEquals(securityItems.get(3).getText(), expectedTextUsers);

    }

    @Test
    public void testManageJenkinsTabSectionStatusInformation() throws InterruptedException {

        WebElement manageJenkinsTab = getDriver().findElement(
                By.xpath("//a[span[text()='Manage Jenkins']]"));
        manageJenkinsTab.click();

        Thread.sleep(1000);

        // Section "Status Information"
        WebElement sectionStatusInformation = getDriver().findElement(
                By.xpath("//h2[@class='jenkins-section__title' and text()='Status Information']"));
        Assert.assertEquals(sectionStatusInformation.getText(), "Status Information");

        List<WebElement> statusInformationItems = getDriver().findElements(
                By.xpath("//h2[@class='jenkins-section__title' and text()='Status Information']/following-sibling::div[@class='jenkins-section__items']//div[@class='jenkins-section__item']"));
        Assert.assertEquals(statusInformationItems.size(), 4);

        String expectedTextSystemInformation = "System Information" + "\n" + "Displays various environmental information to assist trouble-shooting.";
        String expectedTextSystemLog = "System Log" + "\n" + "System log captures output from java.util.logging output related to Jenkins.";
        String expectedTextLoadStatistics = "Load Statistics" + "\n" + "Check your resource utilization and see if you need more computers for your builds.";
        String expectedTextAboutJenkins = "About Jenkins" + "\n" + "See the version and license information.";

        Assert.assertEquals(statusInformationItems.get(0).getText(), expectedTextSystemInformation);
        Assert.assertEquals(statusInformationItems.get(1).getText(), expectedTextSystemLog);
        Assert.assertEquals(statusInformationItems.get(2).getText(), expectedTextLoadStatistics);
        Assert.assertEquals(statusInformationItems.get(3).getText(), expectedTextAboutJenkins);

    }

    @Test
    public void testManageJenkinsTabSectionTroubleshooting() throws InterruptedException {

        WebElement manageJenkinsTab = getDriver().findElement(
                By.xpath("//a[span[text()='Manage Jenkins']]"));
        manageJenkinsTab.click();

        Thread.sleep(1000);

        // Section "Troubleshooting"
        WebElement sectionTroubleshooting = getDriver().findElement(
                By.xpath("//h2[@class='jenkins-section__title' and text()='Troubleshooting']"));
        Assert.assertEquals(sectionTroubleshooting.getText(), "Troubleshooting");

        List<WebElement> troubleshootingItems = getDriver().findElements(
                By.xpath("//h2[@class='jenkins-section__title' and text()='Troubleshooting']/following-sibling::div[@class='jenkins-section__items']//div[@class='jenkins-section__item']"));
        Assert.assertEquals(troubleshootingItems.size(), 1);

        String expectedTextManageOldData = "Manage Old Data" + "\n" + "Scrub configuration files to remove remnants from old plugins and earlier versions.";
        Assert.assertEquals(troubleshootingItems.get(0).getText(), expectedTextManageOldData);

        // Section "Tools and Actions"
        WebElement sectionToolsAndActions = getDriver().findElement(
                By.xpath("//h2[@class='jenkins-section__title' and text()='Tools and Actions']"));
        Assert.assertEquals(sectionToolsAndActions.getText(), "Tools and Actions");

        List<WebElement> toolsAndActionsItems = getDriver().findElements(
                By.xpath("//h2[@class='jenkins-section__title' and text()='Tools and Actions']/following-sibling::div[@class='jenkins-section__items']//div[@class='jenkins-section__item']"));
        Assert.assertEquals(toolsAndActionsItems.size(), 4);

        String expectedTextReloadConfigurationFromDisk = "Reload Configuration from Disk" + "\n" + "Discard all the loaded data in memory and reload everything from file system. Useful when you modified config files directly on disk.";
        String expectedTextJenkinsCLI = "Jenkins CLI" + "\n" + "Access/manage Jenkins from your shell, or from your script.";
        String expectedTextScriptConsole = "Script Console" + "\n" + "Executes arbitrary script for administration/trouble-shooting/diagnostics.";
        String expectedTextPrepareForShutdown = "Prepare for Shutdown" + "\n" + "Stops executing new builds, so that the system can be eventually shut down safely.";

        Assert.assertEquals(toolsAndActionsItems.get(0).getText(), expectedTextReloadConfigurationFromDisk);
        Assert.assertEquals(toolsAndActionsItems.get(1).getText(), expectedTextJenkinsCLI);
        Assert.assertEquals(toolsAndActionsItems.get(2).getText(), expectedTextScriptConsole);
        Assert.assertEquals(toolsAndActionsItems.get(3).getText(), expectedTextPrepareForShutdown);

    }
}
