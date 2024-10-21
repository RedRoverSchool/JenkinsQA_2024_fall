import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class JennQaTest {

    @Test
    public void testImdbCheckFilteredRangeForListOfMovies() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.imdb.com/");
        driver.manage().window().maximize();

        WebElement burgerMenu = driver.findElement(By.id("imdbHeader-navDrawerOpen"));
        burgerMenu.click();

        Thread.sleep(2000);

        WebElement topMoviesButton = driver.findElement(By.xpath("//a[@href='/chart/top/?ref_=nv_mv_250']"));
        topMoviesButton.click();

        WebElement filterButton = driver.findElement(By.xpath("//button[@data-testid='filter-menu-button']"));
        filterButton.click();

        WebElement releaseYearFromField = driver.findElement(By.xpath("//input[@aria-label='Enter release year above']"));
        releaseYearFromField.sendKeys("1970");
        WebElement releaseYearToField = driver.findElement(By.xpath("//input[@aria-label='Enter release year below']"));
        releaseYearToField.sendKeys("1973");
        WebElement closePrompt = driver.findElement(By.xpath("//button[@title='Close Prompt']"));
        closePrompt.click();

        Thread.sleep(2000);

        List<WebElement> resultList = driver.findElements(
                By.xpath("//ul[contains(@class, 'ipc-metadata-list--dividers-between')]//li"));

        boolean allReleaseYearsInRange = true;
        for(WebElement years : resultList) {
            String text = years.getText();
            String[] words = text.split("\\s+");
            for (String word : words) {
                try {
                    int year = Integer.parseInt(word);
                    if ((year < 1970 || year > 1973) && word.length() == 4) {
                        allReleaseYearsInRange = false;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }

        Assert.assertTrue(allReleaseYearsInRange, "There's a movie in the list with release date out of range");

        driver.quit();
    }
}
