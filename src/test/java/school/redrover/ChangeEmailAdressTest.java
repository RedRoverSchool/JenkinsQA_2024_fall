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


public class ChangeEmailAdressTest extends BaseTest {
    @Test
    public void openAccountPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Actions actions = new Actions(getDriver());

        final WebElement chevron = getDriver().findElement(By.xpath("//header/div[3]/a[1]/button"));
        actions.moveToElement(chevron).click().perform();

        final WebElement accountField = getDriver().findElement(By.xpath("//div[3]/div/div/div/a[3]"));
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

        final WebElement chevron = getDriver().findElement(By.xpath("//header/div[3]/a[1]/button"));
        actions.moveToElement(chevron).click().perform();

        final WebElement accountField = getDriver().findElement(By.xpath("//div[3]/div/div/div/a[3]"));
        wait.until(ExpectedConditions.visibilityOf(accountField));
        actions.moveToElement(accountField).click().perform();

        WebElement saveButton = getDriver().findElement(By.xpath("//div[2]/div[2]/form/div[1]/div/div/button[1]")); // - передвигаюсь вних по странице
        actions.moveToElement(saveButton).perform();

        WebElement saveField = getDriver().findElement(By.xpath("//div[2]/div[2]/form/div[1]/section[7]/div[3]/div[3]/input"));

        saveField.clear(); // - Очищаю поле и ввожу новую почту
        saveField.sendKeys("demeshchik.denis@mail.ru");
    }
}
