package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject2Test extends BaseTest {

    private static final String FREESTYLE_NAME = "Freestyle_first";

    public void createFreestyleProject(String nameFreestyleProject) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameFreestyleProject);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void returnToHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void clickJobByName(String nameFreestyleProject) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + nameFreestyleProject + "/']")).click();
    }

    @Test
    public void testEditDescription(){
        final String projectDescription = "my new build";
        final String editDescribe = "Create one more build apps";

        createFreestyleProject(FREESTYLE_NAME);

        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/textarea"))
                .sendKeys(projectDescription);

        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.xpath("//*[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//*[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//*[@name='description']")).clear();
        getDriver().findElement(By.xpath("//*[@name='description']")).sendKeys(editDescribe);
        getDriver().findElement(By.xpath("//*[@id='description']/form/div[2]/button")).click();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(editDescribe));
    }

    @Test
    public void testDeleteFromInsideProject() {
        createFreestyleProject(FREESTYLE_NAME);
        returnToHomePage();
        clickJobByName(FREESTYLE_NAME);

        getDriver().findElement(By.xpath("//a[@data-title='Delete Project']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }

}
