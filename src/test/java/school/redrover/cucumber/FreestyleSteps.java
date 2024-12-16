package school.redrover.cucumber;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import school.redrover.page.freestyle.FreestyleConfigPage;
import school.redrover.page.freestyle.FreestyleProjectPage;
import school.redrover.page.home.CreateNewItemPage;
import school.redrover.page.home.HomePage;
import school.redrover.runner.CucumberDriver;
import school.redrover.runner.ProjectUtils;


public class FreestyleSteps {

    private HomePage homePage;
    private CreateNewItemPage createNewItemPage;

    private FreestyleProjectPage freestyleProjectPage;
    private FreestyleConfigPage freestyleConfigPage;

    @When("Go to NewJob")
    public void goToNewJob() {
        createNewItemPage = new HomePage(CucumberDriver.getDriver()).clickNewItem();
    }

    @And("Choose job type as {string}")
    public void setJobType(String jobType) {
        if ("FreestyleProject".equals(jobType)) {
            createNewItemPage = createNewItemPage.selectFreestyleProject();
        } else if ("Folder".equals(jobType)) {
            createNewItemPage = createNewItemPage.selectFolderType();
        } else {
            throw new RuntimeException("Project type {%s} does not found.".formatted(jobType));
        }
    }

    @And("Choose job type as Freestyle")
    public void setJobTypeAsFreestyle() {
        createNewItemPage = createNewItemPage.selectFreestyleProject();
    }

    @And("Type job name {string}")
    public void enterItemName(String name) {
        createNewItemPage.enterItemName(name);
    }

    @And("Click Ok and go to config")
    public void clickOkAndGoToConfig() {
        freestyleConfigPage = createNewItemPage.selectFreestyleProjectAndClickOk();
    }

    @And("Go home")
    public void goHome() {
        ProjectUtils.get(CucumberDriver.getDriver());
        homePage = new HomePage(CucumberDriver.getDriver());
    }

    @And("Job with name {string} is exists")
    public void checkJobName(String jobName) {
        Assert.assertTrue(homePage.getItemList().contains(jobName));
    }

    @And("Save config and go to Freestyle job")
    public void saveConfigAndGoToFreestyleJob() {
        freestyleProjectPage = freestyleConfigPage
                .clickSaveButton();
    }

    @Then("Freestyle job name is {string}")
    public void assertFreestyleJobName(String jobName) {
        Assert.assertEquals(freestyleProjectPage.getProjectName(), jobName);
    }

    @When("Click Freestyle job {string}")
    public void clickFreestyleJob(String jobName) {
        freestyleProjectPage = new HomePage(CucumberDriver.getDriver())
                .openFreestyleProject(jobName);
    }

    @And("Click Freestyle configure")
    public void clickFreestyleConfigure() {
        freestyleConfigPage = freestyleProjectPage.clickSidebarConfigButton();
    }

    @And("Type Freestyle job description as {string}")
    public void setFreestyleJobDescription(String jobDescription) {
        freestyleConfigPage.enterDescription(jobDescription);
    }

    @Then("Job description is {string}")
    public void assertFreestyleJobDescription(String jobDescription) {
        Assert.assertEquals(freestyleProjectPage.getDescription(), jobDescription);
    }
}
