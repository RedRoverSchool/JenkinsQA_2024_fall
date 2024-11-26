package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;
import java.util.Random;

public class Us_00_001_CreateFreestyleProject extends BaseTest {

    @Test()
    public void createFromTheLeftSidebarMenu(){
        final String generatedName = generateRandomString(7);

//        new HomePage(getDriver()).createFreestyleProject(generatedName);

        List<String>namesOfJobsPresentedInJenkins =
                getDriver().findElements(By.xpath("//div[@id='main-panel']//tbody//tr//td//a//span"))
                        .stream()
                        .map(e -> e.getText())
                        .toList();

        Assert.assertEquals(namesOfJobsPresentedInJenkins.get(0), generatedName);
    }

    @Test
    public void errorMessageDisplayedForEmptyProjectName() {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='itemname-required']")).getText()
                            , "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void errorMessageDisplayedForInvalidCharactersInProjectName(){
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys("#:!@#$%^&");

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='itemname-invalid']")).getText()
                , "» ‘#’ is an unsafe character");

    }

    private String generateRandomString(int stringLength){
        final String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(stringLength);
        Random random = new Random();
        for (int i = 0; i < stringLength; i++) {
            sb.append(AlphaNumericString.charAt(random.nextInt(AlphaNumericString.length())));
        }
        return sb.toString();
    }


}
