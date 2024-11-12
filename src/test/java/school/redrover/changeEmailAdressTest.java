package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class changeEmailAdressTest extends BaseTest {
    @Test
    public void openAccountPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Actions actions = new Actions(getDriver());

        final WebElement chevron = getDriver().findElement(By.xpath("//button[@class='jenkins-menu-dropdown-chevron']"));
        actions.moveToElement(chevron).click().perform();

        final WebElement accountField = getDriver().findElement(By.xpath("//a[@href ='/user/dendemeshchik/account']"));
        wait.until(ExpectedConditions.visibilityOf(accountField));
        actions.moveToElement(accountField).click().perform();

        final String rightAccountURL = "http://localhost:8080/user/dendemeshchik/account/"; // - Сравниваю текущую ссылку
        String accountURL = getDriver().getCurrentUrl();
        Assert.assertEquals(accountURL, rightAccountURL);
    }

    @Test
    public void enterNewMailInAccountSettings() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Actions actions = new Actions(getDriver());

        final WebElement chevron = getDriver().findElement(By.xpath("//button[@class='jenkins-menu-dropdown-chevron']"));
        actions.moveToElement(chevron).click().perform();

        final WebElement accountField = getDriver().findElement(By.xpath("//a[@href ='/user/dendemeshchik/account']"));
        wait.until(ExpectedConditions.visibilityOf(accountField));
        actions.moveToElement(accountField).click().perform();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']")); // - передвигаюсь вних по странице
        actions.moveToElement(saveButton).perform();

        WebElement saveField = getDriver().findElement(By.xpath("//input[@name='email.address']"));

        saveField.clear(); // - Очищаю поле и ввожу новую почту
        saveField.sendKeys("demeshchik.denis@mail.ru");
    }
}
