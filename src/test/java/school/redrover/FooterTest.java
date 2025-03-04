package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

@Ignore
public class FooterTest extends BaseTest {

    private static final String EXPECTED_JENKINS_VERSION = "Jenkins 2.462.3";
    private static final String ABOUT_JENKINS_LABEL = "About Jenkins";

    @Test
    @Epic("12 Footer")
    @Story("US_12.001 Jenkins version")
    @Description("TC_12.001.01 Verify the current Jenkins version number in the footer of the Jenkins dashboard")
    public void testJenkinsVersionOnDashboard() {
        String currentJenkinsVersion = new HomePage(getDriver())
                .getFooter()
                .getJenkinsVersion();

        Allure.step("Expected Result: the current Jenkins version number is correct");
        Assert.assertEquals(currentJenkinsVersion, EXPECTED_JENKINS_VERSION);
    }

    @Test
    @Epic("12 Footer")
    @Story("US_12.001 Jenkins version")
    @Description("TC_12.001.02 Verify version button clickability and display of 'About Jenkins' in dropdown menu")
    public void testJenkinsLabelInDropdown() {
        String actualButtonLabel = new HomePage(getDriver())
                .getFooter()
                .clickJenkinsVersionButton()
                .getAboutJenkinsDropdownLabelText();

        Allure.step("Expected Result: " + ABOUT_JENKINS_LABEL +
                " option is displayed in dropdown menu of Jenkins version number");
        Assert.assertEquals(actualButtonLabel, ABOUT_JENKINS_LABEL);
    }

    @Test
    @Epic("12 Footer")
    @Story("US_12.001 Jenkins version")
    @Description("TC_12.001.02 Verify version button clickability and display of 'About Jenkins' in dropdown menu")
    public void testVerifyTitleDescription() {
        String descriptionTitle = new HomePage(getDriver())
                .getFooter()
                .clickJenkinsVersionButton()
                .gotoAboutPage()
                .getAboutDescription();

        Allure.step("Expected Result: page description displayed correctly");
        Assert.assertEquals(descriptionTitle,
                "The leading open source automation server which enables " +
                        "developers around the world to reliably build, test, and deploy their software.");
    }

    @Test
    @Epic("12 Footer")
    @Story("US_12.001 Jenkins version")
    @Description("TC_12.001.02 Verify version button clickability and display of 'About Jenkins' in dropdown menu")
    public void testCheckNumberOfMavenDependencies() {
        Integer numberOfDependencies = new HomePage(getDriver())
                .getFooter()
                .clickJenkinsVersionButton()
                .gotoAboutPage()
                .getNumberOfMavenDependencies();

        Allure.step("Expected Result: number of maven dependencies displayed on the page is correct");
        Assert.assertEquals(numberOfDependencies, 88);
    }
}