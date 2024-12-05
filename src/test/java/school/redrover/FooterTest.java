package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class FooterTest extends BaseTest {

    private static final String EXPECTED_JENKINS_VERSION = "Jenkins 2.462.3";
    private static final String ABOUT_JENKINS_LABEL = "About Jenkins";

    @Test
    public void testJenkinsVersionOnDashboard() {
        String currentJenkinsVersion = new HomePage(getDriver())
                .getJenkinsVersion();

        Assert.assertEquals(currentJenkinsVersion, EXPECTED_JENKINS_VERSION);
    }

    @Test
    public void testJenkinsLabelInDropdown() {
        String actualButtonLabel = new HomePage(getDriver())
                .clickJenkinsVersionButton()
                .getAboutJenkinsDropdownLabelText();

        Assert.assertEquals(actualButtonLabel, ABOUT_JENKINS_LABEL);
    }

    @Test
    public void checkVersionMainPage() {
        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@type='button']")).
                getText(), EXPECTED_JENKINS_VERSION);
    }

    @Test
    public void testVerifyTitleDescription() {
        String descriptionTitle = new HomePage(getDriver())
                .clickJenkinsVersionButton()
                .gotoAboutPage()
                .getAboutDescription();

        Assert.assertEquals(descriptionTitle,
                "The leading open source automation server which enables " +
                        "developers around the world to reliably build, test, and deploy their software.");
    }

    @Test
    public void testCheckNumberOfMavenDependencies() {
        Integer numberOfDependencies = new HomePage(getDriver())
                .clickJenkinsVersionButton()
                .gotoAboutPage()
                .getNumberOfMavenDependencies();

        Assert.assertEquals(numberOfDependencies, 88);
    }
}