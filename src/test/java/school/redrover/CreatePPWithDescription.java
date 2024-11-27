package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class CreatePPWithDescription extends BaseTest {

    @Test
    public void testCreateWithDescription() {
        final String desc = "The leading open source automation server, Jenkins provides hundreds of plugins to support building, deploying and automating any project.";
        final String name = "PPAndDescription";
        createNewProjectWithDescriptionAndGoHomePageByLogo(name, ProjectType.Pipeline, desc);

        getDriver().findElement(By.xpath("//td/a/span[text()='%s']/..".formatted(name))).click();
        getDriver().findElement(By.xpath("//div[@id='description']/div")).getText();

        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//div[@id='description']/div")).getText(), desc);
    }

    @Test
    public void testCreateWithDescription2() {
        final String description = "The leading open source automation server, Jenkins provides hundreds of plugins to support building, deploying and automating any project.";
        final String projectName = "PipelineProjectAndDescription";

        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .enterDescription(description)
                .clickSaveButton();

        String actualDescription = getDriver().findElement(
                By.xpath("//div[@id='description']")).getText();

        Assert.assertEquals(actualDescription, description);
    }

    @Test
    public void testCreateWithDescription3() {
        final String description = "The leading open source automation server, Jenkins provides hundreds of plugins to support building, deploying and automating any project.";
        final String projectName = "PipelineProjectAndDescription";

        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .enterDescription(description)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, description);
    }

    private void createNewProjectWithDescriptionAndGoHomePageByLogo(String name, ProjectType projectType, String description) {

        getDriver().findElement(By.xpath("//a[@href ='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath(("//div[@id='items']//label/span[text()= '%s']".formatted(projectType))))).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();

        goToHomePageByLogo();
    }

    private void goToHomePageByLogo() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private enum ProjectType {
        Pipeline("Pipeline");

        private final String htmlText;

        ProjectType(String htmlText) {
            this.htmlText = htmlText;
        }

        public String getHtmlText() {
            return htmlText;
        }
    }

}
